package com.azakamu.attendancemanager.application.validators;

/**
 * @author janlingen
 */
public enum VacationValidator {
  ON_WEEKEND("ERROR: Vacation is on a weekend!"),
  NOT_IN_TIMESPAN("ERROR: Vacation is not in the project time span!"),
  START_AFTER_END("ERROR: Vacation start is after vacation end!"),
  NOT_ENOUGH_SPACE_BETWEEN("ERROR: Not enough distance to an existing vacation on this day!"),
  TOO_LONG("ERROR: Vacation too long since there is no exam on this day!"),
  NOT_ENOUGH_TIME_LEFT("ERROR: Not enough remaining vacation left to book this vacation!"),
  SUCCESS("SUCCESS: Vacation booked!"),
  NOT_X_MIN_INTERVAL("ERORR: Vacation start or end is not properly rounded!");

  private final String msg;

  VacationValidator(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
