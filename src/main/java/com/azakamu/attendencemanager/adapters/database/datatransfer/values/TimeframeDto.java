package com.azakamu.attendencemanager.adapters.database.datatransfer.values;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Embeddable;

@Embeddable
public class TimeframeDto {

  LocalDate date;
  LocalTime start;
  LocalTime end;

  public TimeframeDto(LocalDate date, LocalTime start, LocalTime end) {
    this.date = date;
    this.start = start;
    this.end = end;
  }

  public TimeframeDto() {

  }


  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getStart() {
    return start;
  }

  public void setStart(LocalTime start) {
    this.start = start;
  }

  public LocalTime getEnd() {
    return end;
  }

  public void setEnd(LocalTime end) {
    this.end = end;
  }

  @Override
  public String toString() {
    return "TimeframeDto{" +
        "date=" + date +
        ", start=" + start +
        ", end=" + end +
        '}';
  }
}
