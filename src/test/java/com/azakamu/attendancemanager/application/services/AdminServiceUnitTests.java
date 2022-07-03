package com.azakamu.attendancemanager.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.azakamu.attendancemanager.application.repositories.AdminRepository;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author janlingen
 */
public class AdminServiceUnitTests {

  private final AdminRepository adminRepo = mock(AdminRepository.class);
  private AdminService service;

  @BeforeEach
  void setup() {
    service = new AdminService(adminRepo);
  }

  @Test
  @DisplayName("list of log messages gets sorted correctly and returned")
  void getSortedLogsTest1() {
    // arrange
    List<LogMessage> unsorted = new ArrayList<>();
    LogMessage logMessage1 = new LogMessage("12345678", "light-saber training1", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
    LogMessage logMessage2 = new LogMessage("12345679", "light-saber training2", -2L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
    LogMessage logMessage3 = new LogMessage("12345671", "light-saber training3", -3L,
        LocalDateTime.of(2022, 12, 24, 11, 10));
    LogMessage logMessage4 = new LogMessage("12345672", "light-saber training4", -3L,
        LocalDateTime.of(2022, 12, 24, 9, 10));
    unsorted.add(logMessage1);
    unsorted.add(logMessage2);
    unsorted.add(logMessage3);
    unsorted.add(logMessage4);
    when(adminRepo.findAll()).thenReturn(unsorted);

    // act
    List<LogMessage> result = service.getSortedLogs();

    // assert
    assertThat(result.size()).isEqualTo(4);
    assertThat(result.get(0)).isEqualTo(logMessage4);
    assertThat(result.get(1)).isEqualTo(logMessage1);
    assertThat(result.get(2)).isEqualTo(logMessage2);
    assertThat(result.get(3)).isEqualTo(logMessage3);
  }

  @Test
  @DisplayName("saved log message gets returned")
  void saveTest1() {
    // arrange
    LogMessage logMessage = LogMessage.createDummy();
    when(adminRepo.save(logMessage)).thenReturn(logMessage);

    // act
    LogMessage result = service.save(logMessage);

    // assert
    assertThat(result).isEqualTo(logMessage);
  }
}
