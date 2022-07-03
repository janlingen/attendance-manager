package com.azakamu.attendancemanager.domain.values;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TimeframeUnitTests {

  @Test
  @DisplayName("duration() returns the correct amount of minutes")
  void durationTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 10);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    Long duration = timeframe.duration();

    // assert
    assertThat(duration).isEqualTo(240L);
  }

  @Test
  @DisplayName("timeframe is on a weekend")
  void isWeekendTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 19);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    // act
    Boolean weekend = timeframe.isWeekend();

    // assert
    assertThat(weekend).isTrue();
  }

  @Test
  @DisplayName("timeframe is not on a weekend")
  void isWeekendTest2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 18);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    // act
    Boolean weekend = timeframe.isWeekend();

    // assert
    assertThat(weekend).isFalse();
  }

  @Test
  @DisplayName("timeframe.date() is between the given time span")
  void isInTimespanTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 23);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    LocalDate startDate = LocalDate.of(2022, 3, 7);
    LocalDate endDate = LocalDate.of(2022, 3, 25);

    // act
    Boolean inTimespan = timeframe.isInTimespan(startDate, endDate);

    // assert
    assertThat(inTimespan).isTrue();
  }

  @Test
  @DisplayName("timeframe.date() is not between the given time span")
  void isInTimespanTest2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    LocalDate startDate = LocalDate.of(2022, 3, 7);
    LocalDate endDate = LocalDate.of(2022, 3, 25);

    // act
    Boolean inTimespan = timeframe.isInTimespan(startDate, endDate);

    // assert
    assertThat(inTimespan).isFalse();
  }

  @Test
  @DisplayName("timeframe.start() and timeframe.end() modulo 15 minutes is equal to zero")
  void isDividableTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    Boolean dividable = timeframe.isDividable(15);

    // assert
    assertThat(dividable).isTrue();
  }

  @Test
  @DisplayName("timeframe.start() and timeframe.end() modulo 15 minutes isn't equal to zero")
  void isDividableTest2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 32);
    LocalTime end = LocalTime.of(13, 32);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    Boolean dividable = timeframe.isDividable(15);

    // assert
    assertThat(dividable).isFalse();
  }

  @Test
  @DisplayName("both timeframes are on the same date")
  void isSameDayTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 32);
    LocalTime end = LocalTime.of(13, 32);
    Timeframe timeframe1 = new Timeframe(date, start, end);
    Timeframe timeframe2 = new Timeframe(date, start, end);

    // act
    Boolean sameDay = timeframe1.isSameDay(timeframe2);

    // assert
    assertThat(sameDay).isTrue();
  }

  @Test
  @DisplayName("both timeframes are not on the same date")
  void isSameDayTest2() {
    // arrange
    LocalDate date1 = LocalDate.of(2022, 3, 27);
    LocalDate date2 = LocalDate.of(2022, 3, 23);
    LocalTime start = LocalTime.of(9, 32);
    LocalTime end = LocalTime.of(13, 32);
    Timeframe timeframe1 = new Timeframe(date1, start, end);
    Timeframe timeframe2 = new Timeframe(date2, start, end);

    // act
    Boolean sameDay = timeframe1.isSameDay(timeframe2);

    // assert
    assertThat(sameDay).isFalse();
  }


  @Test
  @DisplayName("timeframe1.date() is before timeframe2.date()")
  void isDayBeforeTest1() {
    // arrange
    LocalDate date1 = LocalDate.of(2022, 3, 23);
    LocalDate date2 = LocalDate.of(2022, 3, 24);
    LocalTime start = LocalTime.of(9, 32);
    LocalTime end = LocalTime.of(13, 32);
    Timeframe timeframe1 = new Timeframe(date1, start, end);

    // act
    Boolean sameDay = timeframe1.isDayBefore(LocalDateTime.of(date2, start));

    // assert
    assertThat(sameDay).isTrue();
  }

  @Test
  @DisplayName("timeframe1.date() not is before timeframe2.date()")
  void isDayBeforeTest2() {
    // arrange
    LocalDate date1 = LocalDate.of(2022, 3, 25);
    LocalDate date2 = LocalDate.of(2022, 3, 24);
    LocalTime start = LocalTime.of(9, 32);
    LocalTime end = LocalTime.of(13, 32);
    Timeframe timeframe1 = new Timeframe(date1, start, end);

    // act
    Boolean sameDay = timeframe1.isDayBefore(LocalDateTime.of(date2, start));

    // assert
    assertThat(sameDay).isFalse();
  }

  @Test
  @DisplayName("timeframe.start() is increased correctly")
  void increaseStartTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    LocalTime result = timeframe.increaseStart(15);

    // assert
    assertThat(result).isEqualTo(LocalTime.of(9, 45));
  }

  @Test
  @DisplayName("timeframe.end() is decreased correctly")
  void decreaseEndTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    LocalTime result = timeframe.decreaseEnd(15);

    // assert
    assertThat(result).isEqualTo(LocalTime.of(13, 15));
  }

  @Test
  @DisplayName("toString is formatted correctly")
  void toStringTest1() {
    // arrange
    Timeframe dummy = Timeframe.createDummy();

    // act
    String result = dummy.toString();

    // assert
    assertThat(result).isEqualTo("2021-12-24 from 09:30 to 13:30");
  }

}
