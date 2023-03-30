package com.azakamu.attendancemanager.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.azakamu.attendancemanager.application.repositories.StudentRepository;
import com.azakamu.attendancemanager.application.services.helper.TimeService;
import com.azakamu.attendancemanager.application.validators.VacationValidator;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author janlingen
 */
public class StudentServiceUnitTests {


  private final StudentRepository studentRepo = mock(StudentRepository.class);
  private final ExamService examService = mock(ExamService.class);
  private final TimeService timeService = mock(TimeService.class);
  private final AdminService adminService = mock(AdminService.class);
  private StudentService service;

  @BeforeEach
  void setup() {
    service = new StudentService(studentRepo, examService, timeService, adminService);
    when(timeService.getLocalDateTime()).thenReturn(LocalDateTime.of(2022, 6, 25, 1, 1));
    when(timeService.getTimespanStart()).thenReturn(LocalDate.of(2022, 6, 25));
    when(timeService.getTimespanEnd()).thenReturn(LocalDate.of(2300, 6, 25));
    when(timeService.getExemptionOffsetOffline()).thenReturn(120);
    when(timeService.getExemptionOffsetOnline()).thenReturn(30);
    when(timeService.getVacationTime()).thenReturn(240L);
    when(timeService.getIntervallTime()).thenReturn(15);
    when(timeService.getTimeBetween()).thenReturn(120);
    when(timeService.getMaximumVacationLenght()).thenReturn(150);
  }

  @Test
  @DisplayName("gets a student that exists")
  void getStudentTest1() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    when(studentRepo.findByGithubId(student.getGithubId())).thenReturn(student);

    // act
    Student result = service.getStudent("githubName-dummy", "12345678");

    // assert
    assertThat(result).isEqualTo(student);
  }

  @Test
  @DisplayName("gets a student that doesn't exist and gets created")
  void getStudentTest2() {
    // arrange
    when(studentRepo.findByGithubId("12345678")).thenReturn(Student.createDummy());

    // act
    Student result = service.getStudent("skywalker", "12345678");

    // assert
    verify(studentRepo, times(1)).save(any(Student.class));
  }


  @Test
  @DisplayName("take a vacation, that takes up all of a student vacation time (240min)")
  void enrollVacationTest1() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    LocalDate date = LocalDate.of(2022, 7, 13);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(240);
    assertThat(student.getVacations().get(0).timeframe().date()).isEqualTo(date);
    assertThat(student.getVacations().get(0).timeframe().start()).isEqualTo(start);
    assertThat(student.getVacations().get(0).timeframe().end()).isEqualTo(end);
  }

  @Test
  @DisplayName("take a vacation, that takes up more than all of a student vacation time (240min)")
  void enrollVacationTest2() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    LocalDate date = LocalDate.of(2022, 7, 13);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(14, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.NOT_ENOUGH_TIME_LEFT);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getVacations().size()).isZero();
  }

  @Test
  @DisplayName("take a vacation, that is not in timespan")
  void enrollVacationTest3() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    LocalDate date = LocalDate.of(2022, 5, 13);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(14, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.NOT_IN_TIMESPAN);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getVacations().size()).isZero();
  }

  @Test
  @DisplayName("two existing exams, vacations needs to be splitted correctly")
  void enrollVacationTest4() {
    // arrange
    Timeframe timeframeExam1 = new Timeframe(LocalDate.of(2022, 7, 22), LocalTime.of(8, 30),
        LocalTime.of(10, 0));
    Timeframe timeframeExam2 = new Timeframe(LocalDate.of(2022, 7, 22), LocalTime.of(11, 30),
        LocalTime.of(13, 30));
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    student.addExamId(new ExamId(1L));
    student.addExamId(new ExamId(2L));

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframesByIds(student.getExamIds())).thenReturn(
        List.of(timeframeExam1, timeframeExam2));

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(90);
    assertThat(student.getExamIds().size()).isEqualTo(2);
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getVacations().get(0).timeframe().start()).isEqualTo(LocalTime.of(10, 0));
    assertThat(student.getVacations().get(0).timeframe().end()).isEqualTo(LocalTime.of(11, 30));
    assertThat(student.getVacations().get(0).timeframe().date()).isEqualTo(
        LocalDate.of(2022, 7, 22));
  }

  @Test
  @DisplayName("vacation is in conflict with an existing vacation, they can be merged")
  void enrollVacationTest5() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation = new Vacation(
        new Timeframe(LocalDate.of(2022, 7, 22), LocalTime.of(9, 30), LocalTime.of(10, 0)),
        "light-saber training");
    student.addVacation(vacation);

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 45);
    LocalTime end = LocalTime.of(10, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "mental training with yoda");
    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getVacations()).doesNotContain(vacation);
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(60);
    assertThat(student.getVacations().get(0).timeframe().start()).isEqualTo(
        vacation.timeframe().start());
    assertThat(student.getVacations().get(0).timeframe().end()).isEqualTo(end);
    assertThat(student.getVacations().get(0).timeframe().date()).isEqualTo(date);
    assertThat(student.getVacations().get(0).reason()).isEqualTo(
        "light-saber training; mental training with yoda");
  }

  @Test
  @DisplayName(
      "vacation is in conflict with an existing vacation, they can't be merged, merged vacation would be to long")
  void enrollVacationTest6() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation = new Vacation(
        new Timeframe(LocalDate.of(2022, 7, 22), LocalTime.of(9, 0), LocalTime.of(10, 0)),
        "light-saber training");
    student.addVacation(vacation);

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 45);
    LocalTime end = LocalTime.of(12, 0);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "mental training with yoda");

    // assert
    assertThat(result).isEqualTo(VacationValidator.TOO_LONG);
    assertThat(student.getVacations()).contains(vacation);
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(60);
  }

  @Test
  @DisplayName(
      "vacation is in conflict with an existing vacation, they can be merged, "
          + "merged vacation is to close to another vacation")
  void enrollVacationTest7() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation1 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(11, 30), LocalTime.of(13, 30)), "light-saber training");
    Vacation vacation2 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(9, 30), LocalTime.of(10, 0)), "mental training with yoda");
    student.addVacation(vacation1);
    student.addVacation(vacation2);

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 45);
    LocalTime end = LocalTime.of(10, 15);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "training asoka");

    // assert
    assertThat(result).isEqualTo(VacationValidator.NOT_ENOUGH_SPACE_BETWEEN);
    assertThat(student.getVacations()).contains(vacation1);
    assertThat(student.getVacations()).contains(vacation2);
    assertThat(student.getVacations().size()).isEqualTo(2);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(150);
  }

  @Test
  @DisplayName(
      "vacation is in conflict with an existing vacation, they can be merged, "
          + "merged vacation is far enough from other vacations")
  void enrollVacationTest8() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation1 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(13, 0), LocalTime.of(13, 30)), "light-saber training");
    Vacation vacation2 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(9, 30), LocalTime.of(10, 0)), "mental training with yoda");
    student.addVacation(vacation1);
    student.addVacation(vacation2);

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 45);
    LocalTime end = LocalTime.of(10, 15);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "training asoka");

    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getVacations()).contains(vacation1);
    ;
    assertThat(student.getVacations().size()).isEqualTo(2);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(75);
  }

  @Test
  @DisplayName(
      "vacation is in conflict with an existing vacation, they can be merged, "
          + "merged vacation is splitted by exam")
  void enrollVacationTest9() {
    // arrange
    Timeframe exam =
        new Timeframe(LocalDate.of(2022, 7, 22),
            LocalTime.of(12, 0), LocalTime.of(12, 45));
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(13, 0), LocalTime.of(13, 30)), "light-saber training");
    student.addVacation(vacation);
    student.addExamId(new ExamId(1L));
    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 15);
    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframesByIds(student.getExamIds())).thenReturn(List.of(exam));

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "mental training with yoda");

    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getVacations()).doesNotContain(vacation);
    assertThat(student.getVacations().size()).isEqualTo(2);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(195);
  }

  @Test
  @DisplayName(
      "vacation is in conflict with an existing vacation, they can be merged, "
          + "merged vacation is not splitted by exam and 3h long, but allowed")
  void enrollVacationTest10() {
    // arrange
    Timeframe exam =
        new Timeframe(LocalDate.of(2022, 7, 22),
            LocalTime.of(9, 30), LocalTime.of(10, 30));
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(13, 0), LocalTime.of(13, 30)), "light-saber training");
    student.addVacation(vacation);
    student.addExamId(new ExamId(1L));

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(10, 30);
    LocalTime end = LocalTime.of(13, 15);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframesByIds(student.getExamIds())).thenReturn(List.of(exam));

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "mental training with yoda");

    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getVacations()).doesNotContain(vacation);
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(180L);
  }

  @Test
  @DisplayName("try to enroll for a vacation on a weekend")
  void enrollVacationTest11() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    LocalDate date = LocalDate.of(2022, 7, 23);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.ON_WEEKEND);
    assertThat(student.getVacations().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("enroll for a vacation, when student has existing exams on other days")
  void enrollVacationTest12() {
    // arrange
    Timeframe exam =
        new Timeframe(LocalDate.of(2022, 7, 21),
            LocalTime.of(9, 30), LocalTime.of(13, 30));
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    student.addExamId(new ExamId(1L));

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframesByIds(student.getExamIds())).thenReturn(List.of(exam));

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.SUCCESS);
    assertThat(student.getVacations().size()).isEqualTo(1);
  }


  @Test
  @DisplayName(
      "enroll for a vacation, when student has existing exam on the same day "
          + "and not enought leftover vacation time")
  void enrollVacationTest13() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 20),
                LocalTime.of(9, 30), LocalTime.of(13, 30)), "light-saber training");
    student.addVacation(vacation);

    Timeframe exam =
        new Timeframe(LocalDate.of(2022, 7, 22),
            LocalTime.of(9, 30), LocalTime.of(9, 45));
    student.addExamId(new ExamId(1L));

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(13, 0);
    LocalTime end = LocalTime.of(13, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframesByIds(student.getExamIds())).thenReturn(List.of(exam));

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "mental training with yoda");

    // assert
    assertThat(result).isEqualTo(VacationValidator.NOT_ENOUGH_TIME_LEFT);
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getVacations()).contains(vacation);
  }


  @Test
  @DisplayName("try to enroll vacation that can not be divided by given intervall")
  void enrollVacationTest14() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(9, 22);
    LocalTime end = LocalTime.of(9, 34);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "mental training with yoda");

    // assert
    assertThat(result).isEqualTo(VacationValidator.NOT_X_MIN_INTERVAL);
    assertThat(student.getVacations().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("try to enroll vacation with start after end")
  void enrollVacationTest15() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());

    LocalDate date = LocalDate.of(2022, 7, 22);
    LocalTime start = LocalTime.of(13, 0);
    LocalTime end = LocalTime.of(9, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.START_AFTER_END);
    assertThat(result.getMsg()).isEqualTo("ERROR: Vacation start is after vacation end!");
    assertThat(student.getVacations().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("try to enroll vacation that is too long")
  void enrollVacationTest16() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    LocalDate date = LocalDate.of(2022, 7, 12);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(12, 30);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    VacationValidator result = service.enrollVacation("skywalker", "12345678", date, start, end,
        "light-saber training");

    // assert
    assertThat(result).isEqualTo(VacationValidator.TOO_LONG);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getVacations().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("enroll for a new exam")
  void enrollExamTest1() {
    // arrange
    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 7, 20),
            LocalTime.of(9, 30), LocalTime.of(13, 30));
    ExamId examId = new ExamId(1L);

    Student student1 = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    Student student2 = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    student2.addExamId(examId);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student1);
    when(examService.getTimeframeById(examId)).thenReturn(timeframeExam);
    when(studentRepo.save(any())).thenReturn(student2);

    // act
    service.enrollExam("skywalker", "12345678", 1L);

    // assert
    verify(studentRepo, times(1)).save(student2);
    assertThat(student1.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student1.getExamIds()).contains(examId);
    assertThat(student1.getExamIds().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("try to enroll for same exam twice, unsuccessful")
  void enrollExamTest2() {
    // arrange
    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 3, 24),
            LocalTime.of(9, 30), LocalTime.of(13, 30));
    ExamId examId = new ExamId(1L);

    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    student.addExamId(examId);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframeById(examId)).thenReturn(timeframeExam);

    // act
    service.enrollExam("skywalker", "12345678", 1L);

    // assert
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getExamIds().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("enroll for two different exams on the ")
  void enrollExamTest3() {
    // arrange
    Timeframe timeframeExam1 =
        new Timeframe(LocalDate.of(2022, 7, 24),
            LocalTime.of(9, 30), LocalTime.of(10, 30));
    Timeframe timeframeExam2 =
        new Timeframe(LocalDate.of(2022, 7, 24),
            LocalTime.of(10, 30), LocalTime.of(11, 30));
    ExamId examId1 = new ExamId(1L);
    ExamId examId2 = new ExamId(2L);

    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframeById(examId1)).thenReturn(timeframeExam1);
    when(examService.getTimeframeById(examId2)).thenReturn(timeframeExam2);

    // act
    service.enrollExam("skywalker", "12345678", 1L);
    service.enrollExam("skywalker", "12345678", 2L);

    // assert
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getExamIds().size()).isEqualTo(2);
  }

  @Test
  @DisplayName(
      "enroll exam, existing vacation gets split correctly")
  void enrollExamTest4() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    Vacation vacation =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 14),
                LocalTime.of(9, 30), LocalTime.of(13, 30)), "light-saber training");
    student.addVacation(vacation);

    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 7, 14),
            LocalTime.of(10, 30), LocalTime.of(12, 0));
    ExamId examId = new ExamId(1L);

    when(examService.getTimeframeById(examId)).thenReturn(timeframeExam);
    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    service.enrollExam("skywalker", "12345678", 1L);

    // assert
    assertThat(student.getExamIds()).contains(examId);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(150);
    assertThat(student.getVacations()).doesNotContain(vacation);
    assertThat(student.getVacations().size()).isEqualTo(2);
  }

  @Test
  @DisplayName(
      "enroll exam that takes up the whole day, existing vacation gets removed")
  void enrollExamTest5() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    Vacation vacation =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(9, 30), LocalTime.of(13, 30)), "light-saber training");
    student.addVacation(vacation);

    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 7, 22),
            LocalTime.of(9, 30), LocalTime.of(13, 30));
    ExamId examId = new ExamId(1L);

    when(examService.getTimeframeById(examId)).thenReturn(timeframeExam);
    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    service.enrollExam("skywalker", "12345678", 1L);

    // assert
    assertThat(student.getExamIds()).contains(examId);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getVacations()).doesNotContain(vacation);
    assertThat(student.getVacations().size()).isZero();
  }


  @Test
  @DisplayName(
      "enroll exam , existing vacation is not getting touched")
  void enrollExamTest6() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    Vacation vacation =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 7, 22),
                LocalTime.of(11, 30), LocalTime.of(12, 30)), "light-saber training");
    student.addVacation(vacation);
    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 7, 22),
            LocalTime.of(9, 30), LocalTime.of(10, 0));
    ExamId examId = new ExamId(1L);

    when(examService.getTimeframeById(examId)).thenReturn(timeframeExam);
    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    service.enrollExam("skywalker", "12345678", 1L);

    // assert
    assertThat(student.getExamIds()).contains(examId);
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(60);
  }

  @Test
  @DisplayName("cancel one exam when enrolled for two exams")
  void cancelExamTest1() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    ExamId examId1 = new ExamId(1L);
    ExamId examId2 = new ExamId(2L);
    student.addExamId(examId1);
    student.addExamId(examId2);
    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 7, 10),
            LocalTime.of(11, 0), LocalTime.of(12, 0));

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframeById(examId1)).thenReturn(timeframeExam);
    // act
    service.cancelExam("skywalker", "12345678", 1L);

    // assert
    assertThat(student.getExamIds().size()).isEqualTo(1);
    assertThat(student.getExamIds().stream().toList().get(0)).isEqualTo(examId2);
  }

  @Test
  @DisplayName("try to cancel exam which is already passed")
  void cancelExamTest2() {
    // arrange
    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    ExamId examId1 = new ExamId(1L);
    ExamId examId2 = new ExamId(2L);
    student.addExamId(examId1);
    student.addExamId(examId2);
    Timeframe timeframeExam =
        new Timeframe(LocalDate.of(2022, 3, 7),
            LocalTime.of(11, 0), LocalTime.of(12, 0));

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);
    when(examService.getTimeframeById(examId1)).thenReturn(timeframeExam);

    // act
    service.cancelExam("skywalker", "12345678", 1L);

    // assert
    assertThat(student.getExamIds().size()).isEqualTo(2);
  }

  @Test
  @DisplayName("cancel a vacation")
  void cancelVacationTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 7, 24);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);

    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    Vacation vacation = new Vacation(new Timeframe(date, start, end), "light-saber training");
    student.addVacation(vacation);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    service.cancelVacation("skywalker", "12345678", date, start, end, "light-saber training");

    // assert
    assertThat(student.getVacations().size()).isEqualTo(0);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
  }

  @Test
  @DisplayName("try to cancel vacation which has already passed")
  void cancelVacationTest2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 7);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);

    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());
    Vacation vacation = new Vacation(new Timeframe(date, start, end), "light-saber training");
    student.addVacation(vacation);

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    service.cancelVacation("skywalker", "12345678", date, start, end, "light-saber training");

    // assert
    assertThat(student.getVacations().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("try to cancel exam that doesn't exist")
  void cancelVacationTest3() {
    // arrange
    LocalDate date = LocalDate.of(2022, 7, 9);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);

    Student student = new Student(1L, "skywalker", "12345678",
        new HashSet<>(), new HashSet<>());

    when(studentRepo.findByGithubId("12345678")).thenReturn(student);

    // act
    service.cancelVacation("skywalker", "12345678", date, start, end, "light-saber training");

    // assert
    assertThat(student.getVacations().size()).isEqualTo(0);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
  }
}
