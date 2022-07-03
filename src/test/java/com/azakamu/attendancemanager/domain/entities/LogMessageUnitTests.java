package com.azakamu.attendancemanager.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
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


  @Test
  @DisplayName("two logMessages with equal IDs are equal")
  void equalsTest1() {
    // arrange
    LogMessage logMessage1 = new LogMessage("12345673", "light-saber training1", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
    LogMessage logMessage2 = new LogMessage("12345674", "light-saber training2", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));

    // act
    Boolean equal = logMessage1.equals(logMessage2);

    // assert
    assertThat(equal).isTrue();
  }

  @Test
  @DisplayName("two logMessages with different IDs aren't equal")
  void equalsTest2() {
    // arrange
    LogMessage logMessage1 = new LogMessage("12345673", "light-saber training1", -2L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
    LogMessage logMessage2 = new LogMessage("12345674", "light-saber training2", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));

    // act
    Boolean equal = logMessage1.equals(logMessage2);

    // assert
    assertThat(equal).isFalse();
  }

  @Test
  @DisplayName("logMessage compared with null")
  void equalsTest3() {
    // arrange
    LogMessage logMessage1 = new LogMessage("12345673", "light-saber training1", -2L,
        LocalDateTime.of(2022, 12, 24, 10, 10));

    // act
    Boolean equal = logMessage1.equals(null);

    // assert
    assertThat(equal).isFalse();
  }

  @Test
  @DisplayName("logMessage compared with itself is equal")
  void equalsTest4() {
    // arrange
    LogMessage logMessage1 = new LogMessage("12345673", "light-saber training1", -2L,
        LocalDateTime.of(2022, 12, 24, 10, 10));

    // act
    Boolean equal = logMessage1.equals(logMessage1);

    // assert
    assertThat(equal).isTrue();
  }


  @Test
  @DisplayName("hashCode of two logMessages is equal when they have the same id")
  void hashCodeTest1() {
    // arrange
    LogMessage logMessage1 = new LogMessage("12345673", "light-saber training1", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
    LogMessage logMessage2 = new LogMessage("12345674", "light-saber training2", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));

    // act
    Integer hashCode1 = logMessage1.hashCode();
    Integer hashCode2 = logMessage2.hashCode();

    // assert
    assertThat(hashCode1).isEqualTo(hashCode2);
  }
}
