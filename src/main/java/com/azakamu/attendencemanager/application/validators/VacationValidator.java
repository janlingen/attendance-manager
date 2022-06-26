package com.azakamu.attendencemanager.application.validators;

public enum VacationValidator {
  ON_WEEKEND,
  NOT_IN_TIMESPAN,
  START_AFTER_END,
  NOT_ENOUGH_SPACE_BETWEEN,
  TOO_LONG,
  NOT_ENOUGH_TIME_LEFT,
  SUCCESS,
  NOT_X_MIN_INTERVAL
}
