package com.azakamu.attendancemanager.adapters.database.datatransfer.values;

import javax.persistence.Embeddable;

/**
 * @author janlingen
 */
@Embeddable
public class VacationDto {

  TimeframeDto timeframe;
  String reason;

  public VacationDto() {
  }

  public TimeframeDto getTimeframe() {
    return timeframe;
  }

  public void setTimeframe(
      TimeframeDto timeframe) {
    this.timeframe = timeframe;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

}

