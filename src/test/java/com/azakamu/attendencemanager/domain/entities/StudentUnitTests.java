package com.azakamu.attendencemanager.domain.entities;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.azakamu.attendencemanager.domain.values.Timeframe;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentUnitTests {

  @Test
  @DisplayName("no vacation taken")
  void calcLeftoverVacationTimeTest1() {
    // arrange
    Long leftoverVacationTime = 120L;

    // act
    Student student = new Student(-1L, "janlingen", "123456",
        leftoverVacationTime, Collections.emptyList(), Collections.emptyList());

    // assert
    assertThat(student.getLeftoverVacationTime()).isEqualTo(leftoverVacationTime);
  }

  @Test
  @DisplayName("240 minutes vacation is taken and leftoverVacationTime is correct")
  void calcLeftoverVacationTimeTest2() {
    // arrange
    Vacation vacation = Vacation.createDummy(); // takes 240 minutes vaction;

    // act
    Student student = new Student(-1L, "janlingen", "123456",
        240L, List.of(vacation), Collections.emptyList());

    // assert
    assertThat(student.getLeftoverVacationTime()).isZero();
  }

  @Test
  @DisplayName("vacation is successfully added and leftoverVacationTime is reduced correctly")
  void addVacationTest1() {
    // arrange
    Vacation vacation = Vacation.createDummy(); // takes 240 minutes vaction;
    Student student = new Student(-1L, "janlingen", "123456",
        240L, Collections.emptyList(), Collections.emptyList());

    // act
    student.addVacation(vacation);

    // assert
    assertThat(student.getVacationList().size()).isEqualTo(1);
    assertThat(student.getVacationList().get(0)).isEqualTo(vacation);
    assertThat(student.getLeftoverVacationTime()).isZero();
  }

  @Test
  @DisplayName("vacations are successfully added and leftoverVacationTime is reduced correctly")
  void addVacationsTest1() {
    // arrange
    Vacation vacation1 = new Vacation(Timeframe.createDummy(), "test 1"); // takes 240 minutes vaction;
    Vacation vacation2 = new Vacation(Timeframe.createDummy(), "test 2"); // takes 240 minutes vaction;
    Student student = new Student(-1L, "janlingen", "123456",
        480L, Collections.emptyList(), Collections.emptyList());

    // act
    student.addVacations(List.of(vacation1,vacation2));

    // assert
    assertThat(student.getVacationList().size()).isEqualTo(2);
    assertThat(student.getLeftoverVacationTime()).isZero();
  }


}
