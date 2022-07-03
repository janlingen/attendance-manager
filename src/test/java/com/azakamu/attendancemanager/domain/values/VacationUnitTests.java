package com.azakamu.attendancemanager.domain.values;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VacationUnitTests {

  @Test
  @DisplayName("vacation.timeframe() is in required attendance time")
  void isInRequiredAttendance1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    Vacation vacation = new Vacation(timeframe, "Test reason");

    // act
    Boolean result = vacation.isInRequiredAttendance(LocalTime.of(9, 30),
        LocalTime.of(13, 30));

    // assert
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("vacation.timeframe() is not in required attendance time")
  void isInRequiredAttendance2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    Vacation vacation = new Vacation(timeframe, "Test reason");

    // act
    Boolean result = vacation.isInRequiredAttendance(LocalTime.of(10, 30),
        LocalTime.of(11, 30));

    // assert
    assertThat(result).isFalse();
  }

  @Test
  @DisplayName("vacation1 has valid difference in minutes compared to another vacation2")
  void isValidDifference1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start1 = LocalTime.of(9, 30);
    LocalTime end1 = LocalTime.of(11, 30);
    Timeframe timeframe1 = new Timeframe(date, start1, end1);
    Vacation vacation1 = new Vacation(timeframe1, "Test reason 1");

    LocalTime start2 = LocalTime.of(12, 30);
    LocalTime end2 = LocalTime.of(13, 30);
    Timeframe timeframe2 = new Timeframe(date, start2, end2);
    Vacation vacation2 = new Vacation(timeframe2, "Test reason 2");

    // act
    Boolean result = vacation1.isValidDifference(vacation2, 30);

    // assert
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("vacation1 has no valid difference in minutes compared to another vacation2")
  void isValidDifference2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start1 = LocalTime.of(9, 30);
    LocalTime end1 = LocalTime.of(11, 30);
    Timeframe timeframe1 = new Timeframe(date, start1, end1);
    Vacation vacation1 = new Vacation(timeframe1, "Test reason 1");

    LocalTime start2 = LocalTime.of(11, 30);
    LocalTime end2 = LocalTime.of(13, 30);
    Timeframe timeframe2 = new Timeframe(date, start2, end2);
    Vacation vacation2 = new Vacation(timeframe2, "Test reason 2");

    // act
    Boolean result = vacation1.isValidDifference(vacation2, 30);

    // assert
    assertThat(result).isFalse();
  }

  @Test
  @DisplayName(
      "two vacations are merged, vacation1 starts before vacation2, vacation1 ends before vacation2")
  void mergeTest1() {
    // arrange
    Vacation vacation1 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 3, 20),
                LocalTime.of(11, 30), LocalTime.of(12, 0)), "Test 1");
    Vacation vacation2 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 3, 20),
                LocalTime.of(11, 45), LocalTime.of(13, 30)), "Test 2");

    // act
    Vacation merge = vacation1.merge(vacation2);

    // assert
    assertThat(merge.timeframe().date()).isEqualTo(vacation1.timeframe().date());
    assertThat(merge.timeframe().start()).isEqualTo(vacation1.timeframe().start());
    assertThat(merge.timeframe().end()).isEqualTo(vacation2.timeframe().end());
  }

  @Test
  @DisplayName(
      "two vacations are merged, vacation1 starts before vacation2, vacation1 ends after vacation2")
  void mergeTest2() {
    // arrange
    Vacation vacation1 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 3, 20),
                LocalTime.of(11, 30), LocalTime.of(14, 0)), "Test 1");
    Vacation vacation2 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 3, 20),
                LocalTime.of(11, 45), LocalTime.of(13, 30)), "Test 2");

    // act
    Vacation merge = vacation1.merge(vacation2);

    // assert
    assertThat(merge.timeframe().date()).isEqualTo(vacation1.timeframe().date());
    assertThat(merge.timeframe().start()).isEqualTo(vacation1.timeframe().start());
    assertThat(merge.timeframe().end()).isEqualTo(vacation1.timeframe().end());
  }

  @Test
  @DisplayName(
      "two vacations are merged, vacation1 starts after vacation2, vacation1 ends after vacation2")
  void mergeTest3() {
    // arrange
    Vacation vacation1 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 3, 20),
                LocalTime.of(11, 30), LocalTime.of(13, 0)), "Test 1");
    Vacation vacation2 =
        new Vacation(
            new Timeframe(LocalDate.of(2022, 3, 20),
                LocalTime.of(11, 0), LocalTime.of(12, 30)), "Test 2");

    // act
    Vacation merge = vacation1.merge(vacation2);

    // assert
    assertThat(merge.timeframe().date()).isEqualTo(vacation1.timeframe().date());
    assertThat(merge.timeframe().start()).isEqualTo(vacation2.timeframe().start());
    assertThat(merge.timeframe().end()).isEqualTo(vacation1.timeframe().end());
  }
}


