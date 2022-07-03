package com.azakamu.attendancemanager.domain.entities;

import com.azakamu.attendancemanager.domain.values.ExamId;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * {@link Exam} is an entity that represents the an exam a {@link Student} entity could attend and a
 * {@link Timeframe} which represents the exemption period (including for example arrival and
 * departure). In addition, it contains information whether the exam takes place online or offline,
 * its name and id.
 * <br>
 * <br>
 * E.g. an exam in starts at 11:00 and the exam takes place offline, {@link Timeframe#start()} could
 * be at 10:00 so that the student has 1h for arrival.
 *
 * @author janlingen
 */
public class Exam {

  private final ExamId examId;
  private final String name;

  private final Integer exemptionOffset;

  private final Timeframe timeframe;
  private final Boolean online;

  /**
   * Required arguments constructor, that initializes every class attribute.
   *
   * @param examId          the related {@link ExamId}
   * @param name            the name of the exam
   * @param online          whether the exam takes place offline or online
   * @param exemptionOffset the additional time added at the start and end depending on
   *                        {@link #online}
   * @param timeframe       the {@link Timeframe} in which the exam takes place
   */
  public Exam(ExamId examId, String name, Boolean online, Integer exemptionOffset,
      Timeframe timeframe) {
    this.examId = examId;
    this.name = name;
    this.online = online;
    this.exemptionOffset = exemptionOffset;
    this.timeframe = timeframe;
  }

  /**
   * Creates a dummy instance of Exam.
   *
   * @return an instance of {@link Exam} build with {@link LocalDate} and {@link LocalTime}
   */
  public static Exam createDummy() {
    return new Exam(
        ExamId.createDummy(),
        "Dummy",
        false,
        30,
        Timeframe.createDummy());
  }

  /**
   * Gets the actual exam time without any of the exemption offsets.
   *
   * @return a list containing the adjusted start- and end time
   */
  public List<LocalTime> getReducedExamTime() {
    if (getOnline()) {
      return List.of(timeframe.increaseStart(getExemptionOffset()), timeframe.end());
    } else {
      return List.of(timeframe.increaseStart(getExemptionOffset()),
          timeframe.decreaseEnd(getExemptionOffset()));
    }
  }

  /**
   * Gets the actual exam timeframe without any of the exemption offsets.
   *
   * @return a string build out of the adjusted {@link Timeframe}
   */
  public String getExamTimeframe() {
    List<LocalTime> times = getReducedExamTime();
    return timeframe.date() + ", " + times.get(0) + " - " + times.get(1);
  }

  /**
   * Gets the exam time with exemption time included.
   *
   * @return a string build out of the {@link Timeframe#start()} and {@link Timeframe#end()}
   */
  public String getExamExemptionTime() {
    return timeframe.start() + " - " + timeframe.end();
  }

  // Basic Getter
  // ----------------------------------------------------------------------------------------------
  public ExamId getExamId() {
    return examId;
  }

  public String getName() {
    return name;
  }

  public Integer getExemptionOffset() {
    return exemptionOffset;
  }

  public Timeframe getTimeframe() {
    return timeframe;
  }

  public Boolean getOnline() {
    return online;
  }


  // Equals and Hashcode
  // ----------------------------------------------------------------------------------------------
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Exam exam = (Exam) o;
    return examId.equals(exam.examId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(examId);
  }
}
