package com.azakamu.attendancemanager.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.azakamu.attendancemanager.application.repositories.ExamRepository;
import com.azakamu.attendancemanager.application.services.helper.TimeService;
import com.azakamu.attendancemanager.application.validators.ExamValidator;
import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.values.ExamId;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author janlingen
 */
public class ExamServiceUnitTests {

  private final ExamRepository examRepo = mock(ExamRepository.class);
  private final TimeService timeService = mock(TimeService.class);
  private ExamService service;

  @BeforeEach
  void setup() {
    service = new ExamService(examRepo, timeService);
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
  @DisplayName("load all upcomming exams, exams are successfully filtered")
  void getUpcommingExamsTest1() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    Exam exam2 =
        new Exam(
            ExamId.createDummy(),
            "Defence Against the Dark Arts", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    exams.add(exam2);
    when(examRepo.findAll()).thenReturn(exams);

    // act
    List<Exam> result = service.getUpcommingExams();

    // assert
    assertThat(result).contains(exams.get(1));
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("load all exams, make sure they are sorted")
  void getAllExamsTest1() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    Exam exam2 =
        new Exam(
            ExamId.createDummy(),
            "Defence Against the Dark Arts", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    exams.add(exam2);
    when(examRepo.findAll()).thenReturn(exams);

    // act
    List<Exam> result = service.getAllExams();

    // assert
    assertThat(result).containsAll(exams);
    assertThat(result.size()).isEqualTo(exams.size());
    assertThat(result.get(0)).isEqualTo(exam1);
    assertThat(result.get(1)).isEqualTo(exam2);
  }


  @Test
  @DisplayName("load all exams based on a list of ids")
  void getExamsByIdsTest1() {
    // arrange
    Set<ExamId> examIds = new HashSet<>();
    ExamId examId1 = new ExamId(1L);
    ExamId examId2 = new ExamId(2L);
    examIds.add(examId1);
    examIds.add(examId2);

    List<Exam> exams = new ArrayList<>();
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    Exam exam2 =
        new Exam(
            ExamId.createDummy(),
            "Defence Against the Dark Arts", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    exams.add(exam2);

    when(examRepo.findAllById(examIds.stream().toList())).thenReturn(exams);

    // act
    List<Exam> result = service.getExamsByIds(examIds);

    // assert
    assertThat(result).containsAll(exams);
    assertThat(result.size()).isEqualTo(exams.size());
    assertThat(result.get(0)).isEqualTo(exam1);
    assertThat(result.get(1)).isEqualTo(exam2);
  }

  @Test
  @DisplayName("load exam timeframe based on the given id")
  void getTimeframeByIdTest1() {
    // arrange
    ExamId examId = new ExamId(1L);
    Exam exam =
        new Exam(
            examId,
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));

    when(examRepo.findById(examId)).thenReturn(exam);

    // act
    Timeframe timeframe = service.getTimeframeById(examId);

    // assert
    assertThat(timeframe).isEqualTo(exam.getTimeframe());
  }

  @Test
  @DisplayName("load all exam timeframes based on a list of ids")
  void getTimeframesByIdsTest1() {
    // arrange
    Set<ExamId> examIds = new HashSet<>();
    ExamId examId1 = new ExamId(1L);
    ExamId examId2 = new ExamId(2L);
    examIds.add(examId1);
    examIds.add(examId2);

    List<Exam> exams = new ArrayList<>();
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    Exam exam2 =
        new Exam(
            ExamId.createDummy(),
            "Defence Against the Dark Arts", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    exams.add(exam2);

    when(examRepo.findAllById(examIds.stream().toList())).thenReturn(exams);

    // act
    List<Timeframe> result = service.getTimeframesByIds(examIds);

    // assert
    assertThat(result).containsAll(List.of(exam1.getTimeframe(), exam2.getTimeframe()));
    assertThat(result.size()).isEqualTo(examIds.size());
  }

  @Test
  @DisplayName("offline exam gets created correctly")
  void createExamTest1() {
    // arrange
    String name = "History of Magic";
    LocalDate date = LocalDate.of(2022, 7, 20);
    LocalTime start = LocalTime.of(12, 30);
    LocalTime end = LocalTime.of(12, 45);
    Boolean online = false;

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.SUCCESS);
    verify(examRepo, times(1)).save(any(Exam.class));
    verify(timeService, times(3)).getExemptionOffsetOffline();
    verify(timeService, times(0)).getExemptionOffsetOnline();
  }

  @Test
  @DisplayName("online exam gets created correctly")
  void createExamTest2() {
    // arrange
    String name = "History of Magic";
    LocalDate date = LocalDate.of(2022, 7, 20);
    LocalTime start = LocalTime.of(12, 30);
    LocalTime end = LocalTime.of(12, 45);
    Boolean online = true;

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.SUCCESS);
    verify(examRepo, times(1)).save(any(Exam.class));
    verify(timeService, times(0)).getExemptionOffsetOffline();
    verify(timeService, times(2)).getExemptionOffsetOnline();
  }


  @Test
  @DisplayName("try to create exam with start after end is invalid")
  void createExamTest4() {
    // arrange
    String name = "History of Magic";
    LocalDate date = LocalDate.of(2022, 3, 20);
    LocalTime start = LocalTime.of(12, 45);
    LocalTime end = LocalTime.of(12, 0);
    Boolean online = true;

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.START_AFTER_END);
    verify(examRepo, times(0)).save(any(Exam.class));
  }

  @Test
  @DisplayName("try to create exam with date not in timespan")
  void createExamTest5() {
    // arrange
    String name = "History of Magic";
    LocalDate date = LocalDate.of(2022, 3, 20);
    LocalTime start = LocalTime.of(12, 45);
    LocalTime end = LocalTime.of(13, 0);
    Boolean online = true;

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.NOT_IN_TIMESPAN);
    verify(examRepo, times(0)).save(any(Exam.class));
  }

  @Test
  @DisplayName("two exams with the same name at the same day are not valid")
  void createExamTest6() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    String name = "History of Magic";
    LocalDate date = LocalDate.of(2022, 7, 10);
    LocalTime start = LocalTime.of(12, 30);
    LocalTime end = LocalTime.of(12, 45);
    Boolean online = true;
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    when(examRepo.findAll()).thenReturn(exams);

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.ALREADY_EXISTS);
  }

  @Test
  @DisplayName("two exams with the same name at different days are valid")
  void createExamTest7() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    String name = "History of Magic";
    LocalDate date = LocalDate.of(2022, 8, 10);
    LocalTime start = LocalTime.of(12, 30);
    LocalTime end = LocalTime.of(12, 45);
    Boolean online = true;
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    when(examRepo.findAll()).thenReturn(exams);

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.SUCCESS);
  }

  @Test
  @DisplayName("two exams with the different name and id at the same day are valid")
  void createExamTest8() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    String name = "Defense Against the Dark Arts";
    LocalDate date = LocalDate.of(2022, 7, 10);
    LocalTime start = LocalTime.of(12, 30);
    LocalTime end = LocalTime.of(12, 45);
    Boolean online = true;
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 7, 10),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    when(examRepo.findAll()).thenReturn(exams);

    // act
    ExamValidator result = service
        .createExam(name, date, start, end, online);

    // assert
    assertThat(result).isEqualTo(ExamValidator.SUCCESS);
  }

  @Test
  @DisplayName(
      "sorting exams correctly at the same day and same time")
  void getAllExamsTest2() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    Exam exam2 =
        new Exam(
            ExamId.createDummy(),
            "Defence Against the Dark Arts", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    exams.add(exam2);

    when(examRepo.findAll()).thenReturn(exams);

    // act
    List<Exam> result = service.getAllExams();

    // assert
    assertThat(result).containsAll(exams);
    assertThat(result.size()).isEqualTo(exams.size());
    assertThat(result.get(0)).isEqualTo(exam1);
    assertThat(result.get(1)).isEqualTo(exam2);
  }

  @Test
  @DisplayName(
      "sorting exams correctly at the same day and different times")
  void getAllExamsTest3() {
    // arrange
    List<Exam> exams = new ArrayList<>();
    Exam exam1 =
        new Exam(
            ExamId.createDummy(),
            "History of Magic", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 26),
                LocalTime.of(13, 30), LocalTime.of(14, 0)));
    Exam exam2 =
        new Exam(
            ExamId.createDummy(),
            "Defence Against the Dark Arts", true, timeService.getExemptionOffsetOnline(),
            new Timeframe(LocalDate.of(2022, 6, 26),
                LocalTime.of(10, 30), LocalTime.of(12, 0)));
    exams.add(exam1);
    exams.add(exam2);

    when(examRepo.findAll()).thenReturn(exams);

    // act
    List<Exam> result = service.getAllExams();

    // assert
    assertThat(result).containsAll(exams);
    assertThat(result.size()).isEqualTo(exams.size());
    assertThat(result.get(0)).isEqualTo(exam2);
    assertThat(result.get(1)).isEqualTo(exam1);
  }

}
