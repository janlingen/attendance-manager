package com.azakamu.attendancemanager.domain.values;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExamIdUnitTests {

  @Test
  @DisplayName("dummy.id() has value -1")
  void createDummyTest1() {
    // arrange
    ExamId dummy = ExamId.createDummy();

    // act
    Long result = dummy.id();

    // assert
    assertThat(result).isEqualTo(-1L);
  }
}
