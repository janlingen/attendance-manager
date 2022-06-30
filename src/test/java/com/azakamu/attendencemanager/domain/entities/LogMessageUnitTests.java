package com.azakamu.attendencemanager.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LogMessageUnitTests {

  @Test
  void createDummyTest1() {
    // arrange
    LogMessage logMessage = new LogMessage("12345678", "light-saber training", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));

    // act
    LogMessage result = LogMessage.createDummy();

    // assert
    assertThat(result.getGithubId()).isEqualTo(logMessage.getGithubId());
    assertThat(result.getCreatedAt()).isEqualTo(logMessage.getCreatedAt());
    assertThat(result.getAction()).isEqualTo(logMessage.getAction());
    assertThat(result.getId()).isEqualTo(logMessage.getId());


  }
}
