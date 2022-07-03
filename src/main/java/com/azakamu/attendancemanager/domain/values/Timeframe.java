package com.azakamu.attendancemanager.domain.values;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Timeframe is an immutable object that holds a start- and endtime on a given date.
 *
 * @param date  the date to represent, from MIN_DATE to MAX_DATE
 * @param start the start to represent, from 00:00 to 23:59
 * @param end   the end to represent, from 00:00 to 23:5
 * @author janlingen
 */
public record Timeframe(LocalDate date, LocalTime start, LocalTime end) {

  /**
   * Creates a dummy instance of Timeframe.
   *
   * @return an instance of {@link Timeframe} build with {@link LocalDate} and {@link LocalTime}
   */
  public static Timeframe createDummy() {
    return new Timeframe(LocalDate.of(2021, 12, 24),
        LocalTime.of(9, 30),
        LocalTime.of(13, 30));
  }

  /**
   * Uses the Attributes {@link #start()} and {@link #end()} of a given instance to calculate the
   * time between.
   *
   * @return the time in minutes which lies between a start and end
   */
  public Long duration() {
    return Duration.between(start(), end()).toMinutes();
  }

  /**
   * Checks if the day of {@link #date()} is a saturday or sunday.
   *
   * @return true when a date falls on a weekend, else false
   */
  public Boolean isWeekend() {
    DayOfWeek dateDay = date().getDayOfWeek();
    return dateDay.equals(DayOfWeek.SATURDAY) || dateDay.equals(DayOfWeek.SUNDAY);
  }

  /**
   * Checks if the {@link #date()} of an instance is between a time span.
   *
   * @param startDate the start date of a time span
   * @param endDate   the end date of a time span
   * @return true when a date falls between a given time span, else false
   */
  public Boolean isInTimespan(LocalDate startDate, LocalDate endDate) {
    return date().isAfter(startDate.minusDays(1)) &&
        date().isBefore(endDate.plusDays(1));
  }

  /**
   * Checks if both {@link #start()} and {@link #end()} are values that can be reached by adding up
   * the given minutes over and over.
   *
   * @param minutes the number of minutes into which a time can be divided
   * @return true when a both start and end modulo 15 equals 0, else false
   */
  public Boolean isDividable(Integer minutes) {
    return start().getMinute() % minutes == 0 && end().getMinute() % minutes == 0;
  }

  /**
   * Checks if {@link #date()} equals the date of another {@link Timeframe}
   *
   * @param timeframe the time frame to be compared
   * @return true if {@link #date()} and the date of the given timeframe are equal, else false
   */
  public Boolean isSameDay(Timeframe timeframe) {
    return date().isEqual(timeframe.date());
  }

  /**
   * Checks if {@link #date()} is at least one day before the date of a given {@link LocalDate}
   *
   * @param time the time to be compared with
   * @return true if {@link #date()} is before the given {@link LocalDate}'s date, else false
   */
  public Boolean isDayBefore(LocalDateTime time) {
    return this.date().isBefore(time.toLocalDate());
  }

  /**
   * Adds an exemption offset to {@link Timeframe#start}.
   *
   * @param exemptionOffset the minutes that are added
   * @return {@link Timeframe#start} + exemptionOffset
   */
  public LocalTime increaseStart(Integer exemptionOffset) {
    return this.start().plusMinutes(exemptionOffset);
  }

  /**
   * Subtracts an exemption offset from {@link Timeframe#end}.
   *
   * @param exemptionOffset the minutes that are subtracted
   * @return {@link Timeframe#end} - exemptionOffset
   */
  public LocalTime decreaseEnd(Integer exemptionOffset) {
    return this.end().minusMinutes(exemptionOffset);
  }

  /**
   * Example: 2022-12-24 from 00:01:00 to 23:59:00
   *
   * @return the timeframe's date from start to end
   */
  @Override
  public String toString() {
    return date() + " from " + start() + " to " + end();
  }

}
