package com.azakamu.attendancemanager.adapters.web.datainput;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author janlingen
 */
public record ExamForm(
    String name,
    Boolean onlineExam,
    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @DateTimeFormat(pattern = "HH:mm") LocalTime start,
    @DateTimeFormat(pattern = "HH:mm") LocalTime end) {

  public ExamForm(String name, Boolean onlineExam,
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
      @DateTimeFormat(pattern = "HH:mm") LocalTime start,
      @DateTimeFormat(pattern = "HH:mm") LocalTime end) {
    this.name = name;
    if (onlineExam == null) {
      this.onlineExam = false;
    } else {
      this.onlineExam = onlineExam;
    }
    this.date = date;
    this.start = start;
    this.end = end;
  }
}