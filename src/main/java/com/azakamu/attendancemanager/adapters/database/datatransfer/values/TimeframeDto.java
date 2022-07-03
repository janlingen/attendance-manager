package com.azakamu.attendancemanager.adapters.database.datatransfer.values;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Embeddable;

/**
 * @author janlingen
 */
@Embeddable
public class TimeframeDto {

  LocalDate date;
  LocalTime start;
  LocalTime end;

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
}
