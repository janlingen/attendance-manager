package com.azakamu.attendencemanager.domain.entities;

import com.azakamu.attendencemanager.domain.values.ExamId;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import java.time.LocalTime;
import java.util.List;
import org.apache.tomcat.jni.Local;

/**
 * Exam is an entity that represents the an exam a {@link Student} entity could attend and
 * a {@link Timeframe} which represents the exemption period
 * (including for example arrival and departure). In addition, it contains information whether
 * the exam takes place online or offline, its name and id.
 * <br>
 * <br>
 * E.g. an exam starts at 11:00 and the exam takes place offline,
 * {@link Timeframe#start()} could be at 10:00 so that the student has 1h for arrival.
 */
public class Exam {

  private final ExamId examId;
  private final String name;
  private final Timeframe timeframe;
  private final Boolean online;

  public Exam(ExamId examId, String name, Timeframe timeframe, Boolean online) {
    this.examId = examId;
    this.name = name;
    this.timeframe = timeframe;
    this.online = online;
  }

  private LocalTime increaseStart() {
    if (online) {
      return timeframe.start().plusMinutes(120);
    }
    return timeframe.start().plusMinutes(30);
  }

  private LocalTime decreaseEnd() {
    if (online) {
      return timeframe.end().plusMinutes(-120);
    }
    return timeframe.end();
  }

  public List<LocalTime> getReducedExamTime() {
    return List.of(increaseStart(), decreaseEnd());
  }

  public String getExamTimeframe() {
    List<LocalTime> times = getReducedExamTime();
    return timeframe.date() + ", " + times.get(0) + " - " + times.get(1) + " ";
  }
}
