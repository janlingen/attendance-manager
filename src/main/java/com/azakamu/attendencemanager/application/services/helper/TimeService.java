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
  private final Long vacationTime;
  private final Integer intervallTime;
  private final Integer timeBetween;
  private final Integer maximumVacationLenght;

  public TimeService(@Value("${attendance-manager.timespanStart}") String timespanStart,
      @Value("${attendance-manager.timespanEnd}") String timespanEnd,
      @Value("${attendance-manager.exemptionOffsetOffline}") String exemptionOffsetOffline,
      @Value("${attendance-manager.exemptionOffsetOnline}") String exemptionOffsetOnline,
      @Value("${attendance-manager.vacationTime}") String vacationTime,
      @Value("${attendance-manager.intervallTime}") String intervallTime,
      @Value("${attendance-manager.timeBetween}") String timeBetween,
      @Value("${attendance-manager.maximumVacationLenght}") String maximumVacationLenght) {
    this.timespanStart = LocalDate.parse(timespanStart);
    this.timespanEnd = LocalDate.parse(timespanEnd);
    this.exemptionOffsetOffline = Integer.parseInt(exemptionOffsetOffline);
    this.exemptionOffsetOnline = Integer.parseInt(exemptionOffsetOnline);
    this.vacationTime = Long.parseLong(vacationTime);
    this.intervallTime = Integer.parseInt(intervallTime);
    this.timeBetween = Integer.parseInt(timeBetween);
    this.maximumVacationLenght = Integer.parseInt(maximumVacationLenght);
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

  public Long getVacationTime() {
    return vacationTime;
  }

  public Integer getIntervallTime() {
    return intervallTime;
  }

  public Integer getMaximumVacationLenght() {
    return maximumVacationLenght;
  }

  public Integer getTimeBetween() {
    return timeBetween;
  }
}
