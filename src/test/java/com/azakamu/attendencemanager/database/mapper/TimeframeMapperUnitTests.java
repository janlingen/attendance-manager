package com.azakamu.attendencemanager.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.TimeframeDto;
import com.azakamu.attendencemanager.adapters.database.mapper.TimeframeMapper;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class TimeframeMapperUnitTests {

  TimeframeMapper mapper = Mappers.getMapper(TimeframeMapper.class);

  @Test
  void DomainToDtoTest1() {
    // arrange
    Timeframe timeframe = Timeframe.createDummy();

    // act
    TimeframeDto timeframeDto = mapper.toDto(timeframe);

    // assert
    assertThat(timeframeDto.getDate()).isEqualTo(timeframe.date());
    assertThat(timeframeDto.getStart()).isEqualTo(timeframe.start());
    assertThat(timeframeDto.getEnd()).isEqualTo(timeframe.end());
  }

  @Test
  void DtoToDomainTest1() {
    // arrange
    TimeframeDto timeframeDto = mapper.toDto(Timeframe.createDummy());

    // act
    Timeframe timeframe = mapper.toDomain(timeframeDto);

    // assert
    assertThat(timeframe.date()).isEqualTo(timeframeDto.getDate());
    assertThat(timeframe.start()).isEqualTo(timeframeDto.getStart());
    assertThat(timeframe.end()).isEqualTo(timeframeDto.getEnd());
  }
}
