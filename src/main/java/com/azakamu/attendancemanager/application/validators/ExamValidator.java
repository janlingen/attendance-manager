package com.azakamu.attendancemanager.application.validators;

/**
 * @author janlingen
 */
public enum ExamValidator {
  NOT_IN_TIMESPAN("ERROR: Exam start is after exam end!"),
  START_AFTER_END("ERROR: Exam is not in the project time span!"),
  ALREADY_EXISTS("ERROR: Exam already exists!"),
  SUCCESS("SUCCESS: Exam booked!");

  private final String msg;

  ExamValidator(String msg) {
    this.msg = msg;

  }

  public Object getMsg() {
    return msg;
  }
}
