package com.azakamu.attendencemanager.domain.values;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Vacation is an immutable object that stores a time frame and a reason to provide context.
 *
 * @param timeframe the time frame in which the vacation takes place
 * @param reason    the reason for taking a vacation
 */
public record Vacation(Timeframe timeframe, String reason) {

  public boolean isAtStartOrEnd(Vacation vacation) {
    if (vacation.timeframe().start().equals(LocalTime.of(9, 30))
        && timeframe.end().equals(LocalTime.of(13, 30))) {
      return true;
    }
    return timeframe.start().equals(LocalTime.of(9, 30))
        && vacation.timeframe.end().equals(LocalTime.of(13, 30));
  }

  public boolean isValidDifference(Vacation vacation, Integer diff) {
    return Duration.between(vacation.timeframe().end(), timeframe.start()).toMinutes() >= diff
        || Duration.between(timeframe.end(), vacation.timeframe.start()).toMinutes() >= diff;
  }

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
