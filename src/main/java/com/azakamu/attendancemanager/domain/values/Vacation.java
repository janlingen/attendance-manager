package com.azakamu.attendancemanager.domain.values;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Vacation is an immutable object that stores a time frame and a reason to provide context.
 *
 * @param timeframe the time frame in which the vacation takes place
 * @param reason    the reason for taking a vacation, to be displayed to tutors/admins
 * @author janlingen
 */
public record Vacation(Timeframe timeframe, String reason) {

  /**
   * Creates a dummy instance of Vacation.
   *
   * @return an instance of {@link Vacation} build with {@link Timeframe#createDummy()} and
   * {@link String} "test"
   */
  public static Vacation createDummy() {
    return new Vacation(Timeframe.createDummy(), "test");
  }

  /**
   * Checks if {@link Vacation#timeframe()} is in the given attendance timeframe, otherwise it makes
   * no sense taking a vacation.
   *
   * @param start the attendance start time
   * @param end   the attendance end time
   * @return true if the start of the vacation is after the start parameter and the end of the
   * vacation is after the end parameter
   */
  public Boolean isInRequiredAttendance(LocalTime start, LocalTime end) {
    return timeframe().start().isAfter(start.minusMinutes(1))
        && timeframe().end().isBefore(end.plusMinutes(1));
  }

  /**
   * Checks if there is a valid time difference between the instance and another {@link Vacation}.
   *
   * @param vacation the vacation to compare with
   * @param diff     the difference in minutes required between two vacations
   * @return true if the difference is valid, false if the difference isn't valid
   */
  public Boolean isValidDifference(Vacation vacation, Integer diff) {
    return Duration.between(vacation.timeframe().end(), timeframe.start()).toMinutes() >= diff
        || Duration.between(timeframe.end(), vacation.timeframe.start()).toMinutes() >= diff;
  }

  /**
   * Merges two vacations when they overlap and could actually be one vacation.
   *
   * @param vacation the {@link Vacation} with which the instance gets merged
   * @return a single new merged {@link Vacation}
   */
  public Vacation merge(Vacation vacation) {
    LocalTime start;
    LocalTime end;
    if (timeframe().start().isBefore(vacation.timeframe().start())) {
      start = timeframe().start();
    } else {
      start = vacation.timeframe().start();
    }
    if (timeframe().end().isAfter(vacation.timeframe().end())) {
      end = timeframe().end();
    } else {
      end = vacation.timeframe().end();
    }
    return new Vacation(new Timeframe(timeframe().date(), start, end),
        vacation.reason() + "; " + reason());
  }
}
