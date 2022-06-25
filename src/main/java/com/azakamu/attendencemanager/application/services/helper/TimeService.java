package com.azakamu.attendencemanager.application.services.helper;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

  private final LocalDate timespanStart;
  private final LocalDate timespanEnd;
  private final Integer exemptionOffsetOffline;
  private final Integer exemptionOffsetOnline;

  public TimeService(@Value("${attendance-manager.timespanStart}") String timespanStart,
      @Value("${attendance-manager.timespanEnd}") String timespanEnd,
      @Value("${attendance-manager.exemptionOffsetOffline}") String exemptionOffsetOffline,
      @Value("${attendance-manager.exemptionOffsetOnline}") String exemptionOffsetOnline) {
    this.timespanStart = LocalDate.parse(timespanStart);
    this.timespanEnd = LocalDate.parse(timespanEnd);
    this.exemptionOffsetOffline = Integer.parseInt(exemptionOffsetOffline);
    this.exemptionOffsetOnline = Integer.parseInt(exemptionOffsetOnline);
  }

  public LocalDate getTimespanStart() {
    if (LocalDate.now().isAfter(timespanStart)) {
      return LocalDate.now();
    }
    return timespanStart;
  }

  public LocalDate getTimespanEnd() {
    return timespanEnd;
  }

  public Integer getExemptionOffsetOffline() {
    return exemptionOffsetOffline;
  }

  public Integer getExemptionOffsetOnline() {
    return exemptionOffsetOnline;
  }
}
