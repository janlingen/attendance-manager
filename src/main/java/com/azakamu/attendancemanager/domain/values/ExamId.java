package com.azakamu.attendancemanager.domain.values;

/**
 * ExamId instances are immutable and used to build a reference between a student and an Exam.
 *
 * @param id the unique ID hold by an instance
 * @author janlingen
 */
public record ExamId(Long id) {

  /**
   * Creates a dummy instance of ExamId.
   *
   * @return an instance of {@link ExamId} with id = -1
   */
  public static ExamId createDummy() {
    return new ExamId(-1L);
  }

}
