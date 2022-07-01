package com.azakamu.attendencemanager.adapters.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.ExamDto;
import com.azakamu.attendencemanager.domain.entities.Exam;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author janlingen
 */
public class ExamMapperUnitTests {

  ExamMapper mapper = Mappers.getMapper(ExamMapper.class);
  TimeframeMapper timeframeMapper = Mappers.getMapper(TimeframeMapper.class);

  @Test
  void DomainToDtoTest1() {
    // arrange
    Exam exam = Exam.createDummy();

    // act
    ExamDto examDto = mapper.toDto(exam);

    // assert
    assertThat(examDto.getId()).isEqualTo(exam.getExamId().id());
    assertThat(examDto.getName()).isEqualTo(exam.getName());
    assertThat(examDto.getExemptionOffset()).isEqualTo(exam.getExemptionOffset());
    assertThat(timeframeMapper.toDomain(examDto.getTimeframe())).isEqualTo(exam.getTimeframe());
    assertThat(examDto.getOnline()).isEqualTo(exam.getOnline());
  }

  @Test
  void DtoToDomainTest1() {
    // arrange
    ExamDto examDto = mapper.toDto(Exam.createDummy());

    // act
    Exam exam = mapper.toDomain(examDto);

    // assert
    assertThat(exam).isEqualTo(Exam.createDummy());


  }

}
