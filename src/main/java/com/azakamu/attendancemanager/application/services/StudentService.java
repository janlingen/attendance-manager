package com.azakamu.attendancemanager.application.services;

import com.azakamu.attendancemanager.application.repositories.StudentRepository;
import com.azakamu.attendancemanager.application.services.helper.TimeService;
import com.azakamu.attendancemanager.application.validators.VacationValidator;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
import com.azakamu.attendancemanager.domain.entities.Student;
import com.azakamu.attendancemanager.domain.values.ExamId;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import com.azakamu.attendancemanager.domain.values.Vacation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * @author janlingen
 */
@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final ExamService examService;
  private final TimeService timeService;
  private final AdminService adminService;

  public StudentService(StudentRepository studentRepository, ExamService examService,
      TimeService timeService, AdminService adminService) {
    this.studentRepository = studentRepository;
    this.examService = examService;
    this.timeService = timeService;
    this.adminService = adminService;
  }

  public Student getStudent(String githubName, String githubId) {
    Student query = studentRepository.findByGithubId(githubId);
    if (!query.getGithubId().equals(githubId)) {
      query = createStudent(githubName, githubId);
      adminService.save(
          new LogMessage(githubId,
              "Student with githubId: " + githubId + " and githubName: " + githubName
                  + " was created.", null,
              null));
    }
    return query;
  }

  public VacationValidator enrollVacation(String githubName,
      String githubId, LocalDate date, LocalTime start, LocalTime end, String reason) {
    Timeframe timeframe = new Timeframe(date, start, end);
    if (!timeframe.isDividable(timeService.getIntervallTime())) {
      return VacationValidator.NOT_X_MIN_INTERVAL;
    }
    if (!timeframe.isInTimespan(
        timeService.getTimespanStart(), timeService.getTimespanEnd())
        || LocalDateTime.of(timeframe.date(), timeframe.start())
        .isBefore(timeService.getLocalDateTime())) {
      return VacationValidator.NOT_IN_TIMESPAN;
    }
    if (timeframe.isWeekend()) {
      return VacationValidator.ON_WEEKEND;
    }
    if (!timeframe.start().isBefore(timeframe.end())) {
      return VacationValidator.START_AFTER_END;
    }
    Vacation vacation = new Vacation(timeframe, reason);
    Student query = getStudent(githubName, githubId);
    VacationValidator result =
        bookVacation(query, examService.getTimeframesByIds(query.getExamIds()), vacation);
    studentRepository.save(query);
    adminService.save(new LogMessage(githubId,
        "Student with githubId: " + githubId + " updated his vacations.", null, null));
    return result;
  }

  public void cancelVacation(String githubName, String githubId, LocalDate date, LocalTime start,
      LocalTime end, String reason) {
    Student query = getStudent(githubName, githubId);
    Timeframe timeframe = new Timeframe(date, start, end);
    Vacation vacation = new Vacation(timeframe, reason);
    if (query.getVacations().contains(vacation)) {
      if (date.isAfter(timeService.getTimespanStart())) {
        query.removeVacation(vacation);
        studentRepository.save(query);
        adminService.save(new LogMessage(githubId,
            "Student with githubId: " + githubId + " removed a vacation on date: " + date + " .",
            null, null));
      }
    }
  }

  public void enrollExam(String githubName, String githubId, Long examId) {
    Student query = getStudent(githubName, githubId);
    if (!query.getExamIds().contains(new ExamId(examId))) {
      bookExam(
          query,
          new ExamId(examId),
          examService.getTimeframeById(new ExamId(examId)));
      studentRepository.save(query);
      adminService.save(new LogMessage(githubId,
          "Student with githubId: " + githubId + " enrolled for exam with id: " + examId + " .",
          null, null));
    }
  }

  public void cancelExam(String githubName, String githubId, Long examId) {
    if (examService.getTimeframeById(new ExamId(examId))
        .date()
        .isAfter(timeService.getTimespanStart())) {
      Student query = getStudent(githubName, githubId);
      query.removeExamId(new ExamId(examId));
      studentRepository.save(query);
      adminService.save(
          new LogMessage(githubId,
              "Student with githubId: " + githubId + " removed exam with id: " + examId + " .",
              null,
              null));
    }
  }

  private Student createStudent(String githubName, String githubId) {
    return studentRepository.save(
        new Student(null, githubName, githubId,
            Collections.emptySet(), Collections.emptySet()));
  }

  private VacationValidator bookVacation(Student student, List<Timeframe> examTimeframes,
      Vacation vacation) {
    Vacation newVacation = vacation;
    Set<Vacation> old = new HashSet<>();
    Set<Vacation> sameDayNoOverlap = new HashSet<>();
    Long deletedTime = 0L;
    Boolean exanAtSameDay = checkSameDayForExam(vacation.timeframe(), examTimeframes);
    for (Vacation v : student.getVacations()) {
      // other vacation on same day
      if (v.timeframe().isSameDay(newVacation.timeframe())) {
        if (!(newVacation.timeframe().end().isBefore(v.timeframe().start())
            || newVacation.timeframe().start().isAfter(v.timeframe().end()))) {
          deletedTime -= v.timeframe().duration();
          old.add(v);
          newVacation = newVacation.merge(v);
        } else {
          if (!exanAtSameDay) {
            sameDayNoOverlap.add(v);

          }
        }
      }
    }
    for (Vacation v : sameDayNoOverlap) {
      if (!newVacation.isValidDifference(v, timeService.getTimeBetween())) {
        return VacationValidator.NOT_ENOUGH_SPACE_BETWEEN;
      }
    }
    if (exanAtSameDay) {
      Set<Vacation> splitables = new HashSet<>();
      splitables.add(newVacation);
      for (Timeframe t : examTimeframes) {
        if (vacation.timeframe().isSameDay(t)) {
          splitables = splitVacations(t, splitables);
        }
      }
      Long duration = 0L;
      for (Vacation v : splitables) {
        duration += v.timeframe().duration();
      }
      if ((timeService.getVacationTime() - (student.getAggregatedVacationTime() + deletedTime))
          < duration) {
        return VacationValidator.NOT_ENOUGH_TIME_LEFT;
      }
      student.removeVacations(old);
      student.addVacations(splitables);
      return VacationValidator.SUCCESS;
    }

    if ((timeService.getVacationTime() - (student.getAggregatedVacationTime() + deletedTime))
        < newVacation.timeframe().duration()) {
      return VacationValidator.NOT_ENOUGH_TIME_LEFT;
    }
    if ((newVacation.timeframe().duration() > timeService.getMaximumVacationLenght()
        && newVacation.timeframe().duration() < timeService.getVacationTime())
        || newVacation.timeframe().duration() > timeService.getVacationTime()) {
      return VacationValidator.TOO_LONG;
    }
    student.removeVacations(old);
    student.addVacation(newVacation);
    return VacationValidator.SUCCESS;
  }

  private Boolean checkSameDayForExam(Timeframe timeframe, List<Timeframe> examTimeframes) {
    for (Timeframe klausur : examTimeframes) {
      if (klausur.date().isEqual(timeframe.date())) {
        return true;
      }
    }
    return false;
  }

  private Set<Vacation> splitVacations(Timeframe examTimeframe,
      Set<Vacation> vacations) {
    Set<Vacation> out = new HashSet<>();
    for (Vacation v : vacations) {
      if (!(v.timeframe().end().isBefore(examTimeframe.start())
          || v.timeframe().start().isAfter(examTimeframe.end()))) {
        if (v.timeframe().start().isBefore(examTimeframe.start())) {
          out.add(
              new Vacation(new Timeframe(v.timeframe().date(), v.timeframe().start(),
                  examTimeframe.start()), v.reason()));
        }
        if (v.timeframe().end().isAfter(examTimeframe.end())) {
          out.add(new Vacation(
              new Timeframe(v.timeframe().date(), examTimeframe.end(), v.timeframe().end()),
              v.reason()));
        }
      } else {
        out.add(v);
      }
    }
    return out;
  }

  private void bookExam(Student student, ExamId examId, Timeframe examTimeframe) {
    List<Vacation> vacations = student.getVacations();
    for (Vacation v : vacations) {
      if (examTimeframe.isSameDay(v.timeframe()) && !(
          v.timeframe().end().isBefore(examTimeframe.start())
              || v.timeframe().start().isAfter(examTimeframe.end()))) {
        student.addVacations(splitVacations(examTimeframe, Set.of(v)));
        student.removeVacation(v);
      }
    }
    student.addExamId(examId);
  }

}
