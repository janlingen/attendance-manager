package com.azakamu.attendencemanager.adapters.database.datatransfer.values;

import javax.persistence.Embeddable;

@Embeddable
public class VacationDto {

  TimeframeDto timeframe;
  String reason;

  public VacationDto(TimeframeDto timeframe, String reason) {
    this.timeframe = timeframe;
    this.reason = reason;
  }

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

  @Override
  public String toString() {
    return "VacationDto{" +
        "timeframe=" + timeframe +
        ", reason='" + reason + '\'' +
        '}';
  }
}

