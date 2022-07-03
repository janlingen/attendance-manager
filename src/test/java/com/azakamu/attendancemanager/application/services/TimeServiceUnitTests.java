package com.azakamu.attendancemanager.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendancemanager.application.services.helper.TimeService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author janlingen
 */
public class TimeServiceUnitTests {

  private TimeService service;


  @Test
  @DisplayName("check if time fractions are generated correctly")
  void getTimefractionsTest1() {
    // arrange & act
    LocalDate now = LocalDate.now();
    service = new TimeService(now.toString(), now.plusYears(1).toString(), "08:30",
        "13:30", "120", "30",
        "240", "15", "120", "150");

    List<String> result = service.getTimefractions();

    // assert
    assertThat(result.size()).isNotZero();
    assertThat(result.get(0)).isEqualTo("08:30");
    assertThat(result.get(result.size() - 1)).isEqualTo("13:30");
  }

  @Test
  @DisplayName("check if properties are set correctly, LocalDate.now() is not after timespanStart")
  void checkSetupTest1() {
    // arrange
    LocalDate now = LocalDate.now();
    service = new TimeService(now.toString(), now.plusYears(1).toString(), "08:30",
        "13:30", "120", "30",
        "240", "15", "120", "150");

    // act & assert
    assertThat(service.getTimespanStart()).isEqualTo(now);
    assertThat(service.getTimespanEnd()).isEqualTo(now.plusYears(1));
    assertThat(service.getExemptionOffsetOffline()).isEqualTo(120);
    assertThat(service.getExemptionOffsetOnline()).isEqualTo(30);
    assertThat(service.getVacationTime()).isEqualTo(240);
    assertThat(service.getIntervallTime()).isEqualTo(15);
    assertThat(service.getTimeBetween()).isEqualTo(120);
    assertThat(service.getMaximumVacationLenght()).isEqualTo(150);
  }

  @Test
  @DisplayName("check if properties are set correctly, LocalDate.now() is after timespanStart")
  void checkSetupTest2() {
    // arrange
    LocalDate now = LocalDate.now();
    service = new TimeService(now.minusYears(1).toString(), now.plusYears(1).toString(), "08:30",
        "13:30", "120", "30",
        "240", "15", "120", "150");

    // act & assert
    assertThat(service.getTimespanStart()).isEqualTo(now);
    assertThat(service.getTimespanEnd()).isEqualTo(now.plusYears(1));
    assertThat(service.getExemptionOffsetOffline()).isEqualTo(120);
    assertThat(service.getExemptionOffsetOnline()).isEqualTo(30);
    assertThat(service.getVacationTime()).isEqualTo(240);
    assertThat(service.getIntervallTime()).isEqualTo(15);
    assertThat(service.getTimeBetween()).isEqualTo(120);
    assertThat(service.getMaximumVacationLenght()).isEqualTo(150);
  }

}
