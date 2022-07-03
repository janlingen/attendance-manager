package com.azakamu.attendancemanager.application.services;

import com.azakamu.attendancemanager.application.repositories.ExamRepository;
import com.azakamu.attendancemanager.application.services.helper.TimeService;
import com.azakamu.attendancemanager.application.validators.ExamValidator;
import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.values.ExamId;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author janlingen
 */
@Service
public class ExamService {

  private final ExamRepository examRepository;
  private final TimeService timeService;

  public ExamService(ExamRepository examRepository, TimeService timeService) {
    this.examRepository = examRepository;
    this.timeService = timeService;
  }

  public List<Exam> getUpcommingExams() {
    List<Exam> exams = examRepository.findAll().stream().filter(this::filterPastExam)
        .collect(Collectors.toList());
    sortExams(exams);
    return exams;
  }

  public List<Exam> getAllExams() {
    List<Exam> exams = examRepository.findAll();
    sortExams(exams);
    return exams;
  }

  public List<Exam> getExamsByIds(Set<ExamId> examIds) {
    List<Exam> exams = examRepository.findAllById(examIds.stream().toList());
    sortExams(exams);
    return exams;
  }

  public Timeframe getTimeframeById(ExamId examId) {
    return examRepository.findById(examId).getTimeframe();
  }

  public List<Timeframe> getTimeframesByIds(Set<ExamId> examIds) {
    return getExamsByIds(examIds).stream().map(Exam::getTimeframe)
        .collect(Collectors.toList());
  }

  public ExamValidator createExam(String name, LocalDate date, LocalTime start,
      LocalTime end, Boolean online) {
    Exam exam;
    if (!start.isBefore(end)) {
      return ExamValidator.START_AFTER_END;
    }
    if (online) {
      exam =
          new Exam(ExamId.createDummy(), name, online, timeService.getExemptionOffsetOnline(),
              new Timeframe(date, start.minusMinutes(timeService.getExemptionOffsetOnline()), end));
    } else {
      exam =
          new Exam(ExamId.createDummy(), name, online, timeService.getExemptionOffsetOffline(),
              new Timeframe(date, start.minusMinutes(timeService.getExemptionOffsetOffline()),
                  end.plusMinutes(
                      timeService.getExemptionOffsetOffline())));
    }
    if (!exam
        .getTimeframe()
        .isInTimespan(timeService.getTimespanStart(), timeService.getTimespanEnd())) {
      return ExamValidator.NOT_IN_TIMESPAN;
    }
    if (checkDayForEqual(date, name)) {
      return ExamValidator.ALREADY_EXISTS;
    }
    saveExam(exam);
    return ExamValidator.SUCCESS;
  }

  private void saveExam(Exam exam) {
    examRepository.save(exam);
  }

  private void sortExams(List<Exam> exams) {
    exams.sort(
        (o1, o2) -> {
          if (o1.getTimeframe()
              .date()
              .atTime(o1.getTimeframe().start())
              .isBefore(o2.getTimeframe().date().atTime(o2.getTimeframe().start()))) {
            return -1;
          } else if (o1.getTimeframe()
              .date()
              .atTime(o1.getTimeframe().start())
              .isAfter(o2.getTimeframe().date().atTime(o2.getTimeframe().start()))) {
            return 1;
          }
          return 0;
        });
  }

  private Boolean filterPastExam(Exam exam) {
    return exam.getTimeframe().date().isAfter(timeService.getTimespanStart());
  }

  private Boolean checkDayForEqual(LocalDate date, String name) {
    for (Exam exam : getAllExams()) {
      if (exam.getTimeframe().date().equals(date) && exam.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

}
