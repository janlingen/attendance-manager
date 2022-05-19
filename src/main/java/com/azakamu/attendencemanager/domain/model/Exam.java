package com.azakamu.attendencemanager.domain.model;

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
}
