package com.azakamu.attendancemanager.application.services.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author janlingen
 */
@Service
public class TimeService {

  private final LocalDate timespanStart;
  private final LocalDate timespanEnd;
  private final LocalTime dailyStart;
  private final LocalTime dailyEnd;
  private final Integer exemptionOffsetOffline;
  private final Integer exemptionOffsetOnline;
  private final Long vacationTime;
  private final Integer intervallTime;
  private final Integer timeBetween;
  private final Integer maximumVacationLenght;
  // FIXME: no long term solution
  private final List<String> examTimes =
      List.of(
          "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
          "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
          "19:00");

  public TimeService(@Value("${attendance-manager.timespanStart}") String timespanStart,
      @Value("${attendance-manager.timespanEnd}") String timespanEnd,
      @Value("${attendance-manager.dailyStart}") String dailyStart,
      @Value("${attendance-manager.dailyEnd}") String dailyEnd,
      @Value("${attendance-manager.exemptionOffsetOffline}") String exemptionOffsetOffline,
      @Value("${attendance-manager.exemptionOffsetOnline}") String exemptionOffsetOnline,
      @Value("${attendance-manager.vacationTime}") String vacationTime,
      @Value("${attendance-manager.intervallTime}") String intervallTime,
      @Value("${attendance-manager.timeBetween}") String timeBetween,
      @Value("${attendance-manager.maximumVacationLenght}") String maximumVacationLenght) {
    this.timespanStart = LocalDate.parse(timespanStart);
    this.timespanEnd = LocalDate.parse(timespanEnd);
    this.dailyStart = LocalTime.parse(dailyStart);
    this.dailyEnd = LocalTime.parse(dailyEnd);
    this.exemptionOffsetOffline = Integer.parseInt(exemptionOffsetOffline);
    this.exemptionOffsetOnline = Integer.parseInt(exemptionOffsetOnline);
    this.vacationTime = Long.parseLong(vacationTime);
    this.intervallTime = Integer.parseInt(intervallTime);
    this.timeBetween = Integer.parseInt(timeBetween);
    this.maximumVacationLenght = Integer.parseInt(maximumVacationLenght);
  }

  public List<String> getTimefractions() {
    List<LocalTime> vacationTimes = new ArrayList<>();
    LocalTime toAdd = dailyStart;
    while (toAdd.isBefore(dailyEnd)) {
      vacationTimes.add(toAdd);
      toAdd = toAdd.plusMinutes(intervallTime);
    }
    vacationTimes.add(dailyEnd);
    return vacationTimes.stream().map(LocalTime::toString).collect(Collectors.toList());

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

  public LocalDateTime getLocalDateTime() {
    return LocalDateTime.now();
  }
}
