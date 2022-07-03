package com.azakamu.attendancemanager.domain.entities;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.azakamu.attendancemanager.domain.values.ExamId;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import com.azakamu.attendancemanager.domain.values.Vacation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentUnitTests {

  @Test
  @DisplayName("no vacation taken")
  void calcLeftoverVacationTimeTest1() {
    // arrange & act
    Student student = new Student(-1L, "janlingen", "123456", Collections.emptySet(),
        Collections.emptySet());

    // assert
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
  }

  @Test
  @DisplayName("240 minutes vacation is taken and aggregatedVacationTime is correct")
  void calcLeftoverVacationTimeTest2() {
    // arrange
    Vacation vacation = Vacation.createDummy(); // takes 240 minutes vaction;

    // act
    Student student = new Student(-1L, "janlingen", "123456",
        Set.of(vacation), Collections.emptySet());

    // assert
    assertThat(student.getAggregatedVacationTime()).isEqualTo(240);
  }

  @Test
  @DisplayName("vacation is successfully added and aggregatedVacationTime is increased correctly")
  void addVacationTest1() {
    // arrange
    Vacation vacation = Vacation.createDummy(); // takes 240 minutes vaction;
    Student student = new Student(-1L, "janlingen", "123456", Collections.emptySet(),
        Collections.emptySet());

    // act
    student.addVacation(vacation);

    // assert
    assertThat(student.getVacations().size()).isEqualTo(1);
    assertThat(student.getVacations().get(0)).isEqualTo(vacation);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(240);
  }

  @Test
  @DisplayName("vacations are successfully added and aggregatedVacationTime is increased correctly")
  void addVacationsTest1() {
    // arrange
    Vacation vacation1 = new Vacation(Timeframe.createDummy(),
        "test 1"); // takes 240 minutes vaction;
    Vacation vacation2 = new Vacation(Timeframe.createDummy(),
        "test 2"); // takes 240 minutes vaction;
    Student student = new Student(-1L, "janlingen", "123456", Collections.emptySet(),
        Collections.emptySet());

    // act
    student.addVacations(List.of(vacation1, vacation2));

    // assert
    assertThat(student.getVacations().size()).isEqualTo(2);
    assertThat(student.getAggregatedVacationTime()).isEqualTo(480);
  }

  @Test
  @DisplayName("vacation is successfully removed and aggregatedVacationTime is decreased correctly")
  void removeVacationTest1() {
    // arrange
    Vacation vacation = Vacation.createDummy(); // takes 240 minutes vaction;
    Student student = new Student(-1L, "janlingen", "123456"
        , Set.of(vacation), Collections.emptySet());

    // act
    student.removeVacation(vacation);

    // assert
    assertThat(student.getVacations().size()).isZero();
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
  }

  @Test
  @DisplayName("vacations are successfully removed and aggregatedVacationTime is decreased correctly")
  void removeVacationsTest1() {
    // arrange
    Vacation vacation1 = new Vacation(Timeframe.createDummy(),
        "test 1"); // takes 240 minutes vaction;
    Vacation vacation2 = new Vacation(Timeframe.createDummy(),
        "test 2"); // takes 240 minutes vaction;
    Student student = new Student(-1L, "janlingen", "123456"
        , Set.of(vacation1, vacation2), Collections.emptySet());

    // act
    student.removeVacations(Set.of(vacation1, vacation2));

    // assert
    assertThat(student.getVacations().size()).isZero();
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
  }

  @Test
  @DisplayName("examId is successfully added")
  void addExamIdTest1() {
    // arrange
    ExamId examId = ExamId.createDummy();
    Student student = Student.createDummy();

    // act
    student.addExamId(examId);

    // assert
    assertThat(student.getExamIds().size()).isEqualTo(1);
  }

  @Test
  @DisplayName("examId is successfully removed")
  void removeExamIdTest1() {
    // arrange
    ExamId examId = ExamId.createDummy();
    Student student = new Student(-1L, "janlingen", "123456", Collections.emptySet(),
        Set.of(examId));

    // act
    student.removeExamId(examId);

    // assert
    assertThat(student.getExamIds().size()).isZero();
  }

  @Test
  @DisplayName("vacations are sorted correctly")
  void sortVacationsTest1() {
    // arrange
    Timeframe timeframe1 = new Timeframe(LocalDate.of(2022, 12, 24),
        LocalTime.of(11, 30),
        LocalTime.of(12, 30));
    Timeframe timeframe2 = new Timeframe(LocalDate.of(2022, 12, 24),
        LocalTime.of(9, 30),
        LocalTime.of(10, 30));
    Timeframe timeframe3 = new Timeframe(LocalDate.of(2022, 12, 25),
        LocalTime.of(11, 30),
        LocalTime.of(12, 30));
    Timeframe timeframe4 = new Timeframe(LocalDate.of(2022, 12, 23),
        LocalTime.of(11, 30),
        LocalTime.of(12, 30));
    Vacation vacation1 = new Vacation(timeframe1, "test 1");
    Vacation vacation2 = new Vacation(timeframe2, "test 1");
    Vacation vacation3 = new Vacation(timeframe3, "test 1");
    Vacation vacation4 = new Vacation(timeframe4, "test 1");

    // act
    Student student = new Student(-1L, "janlingen", "123456",
        Set.of(vacation1, vacation2, vacation3, vacation4), Collections.emptySet());

    // assert
    assertThat(student.getVacations().get(1)).isEqualTo(vacation2);
    assertThat(student.getVacations().get(2)).isEqualTo(vacation1);
    assertThat(student.getVacations().get(3)).isEqualTo(vacation3);
    assertThat(student.getVacations().get(0)).isEqualTo(vacation4);
  }

  @Test
  @DisplayName("dummy is build correctly")
  void createDummyTest1() {
    // arrange & act
    Student student = Student.createDummy();

    // assert
    assertThat(student.getId()).isEqualTo(-1L);
    assertThat(student.getGithubName()).isEqualTo("githubName-dummy");
    assertThat(student.getGithubId()).isEqualTo("githubId-dummy");
    assertThat(student.getAggregatedVacationTime()).isEqualTo(0);
    assertThat(student.getVacations().size()).isZero();
    assertThat(student.getExamIds().size()).isZero();
  }

  @Test
  @DisplayName("two students with equal IDs are equal")
  void equalsTest1() {
    // arrange
    Student student1 = new Student(-1L, "lukeskywalker", "654321",
        Collections.emptySet(), Collections.emptySet());
    Student student2 = new Student(-1L, "janlingen", "123456",
        Collections.emptySet(), Collections.emptySet());

    // act
    Boolean equal = student1.equals(student2);

    // assert
    assertThat(equal).isTrue();
  }

  @Test
  @DisplayName("two students with different IDs aren't equal")
  void equalsTest2() {
    // arrange
    // arrange
    Student student1 = new Student(-2L, "janlingen", "123456",
        Collections.emptySet(), Collections.emptySet());
    Student student2 = new Student(-1L, "janlingen", "123456",
        Collections.emptySet(), Collections.emptySet());

    // act
    Boolean equal = student1.equals(student2);

    // assert
    assertThat(equal).isFalse();
  }

  @Test
  @DisplayName("student compared with null")
  void equalsTest3() {
    // arrange
    Student student = new Student(-2L, "janlingen", "123456",
        Collections.emptySet(), Collections.emptySet());
    // act
    Boolean equal = student.equals(null);

    // assert
    assertThat(equal).isFalse();
  }

  @Test
  @DisplayName("student compared with itself is equal")
  void equalsTest4() {
    // arrange
    Student student = new Student(-2L, "janlingen", "123456",
        Collections.emptySet(), Collections.emptySet());

    // act
    Boolean equal = student.equals(student);

    // assert
    assertThat(equal).isTrue();
  }


  @Test
  @DisplayName("hashCode of two exams is equal when they have the same ExamId")
  void hashCodeTest1() {
    // arrange
    Student student1 = new Student(-1L, "lukeskywalker", "654321",
        Collections.emptySet(), Collections.emptySet());
    Student student2 = new Student(-1L, "janlingen", "123456",
        Collections.emptySet(), Collections.emptySet());

    // act
    Integer hashCode1 = student1.hashCode();
    Integer hashCode2 = student2.hashCode();

    // assert
    assertThat(hashCode1).isEqualTo(hashCode2);
  }

}
