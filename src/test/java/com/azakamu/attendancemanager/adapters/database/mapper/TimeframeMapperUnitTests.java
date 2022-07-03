package com.azakamu.attendancemanager.adapters.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendancemanager.adapters.database.datatransfer.values.TimeframeDto;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * !!! Overdeveloped, but useful for educational purposes, but normally the first thing you should
 * do is rely on a class generating library like mapstruct being adequately tested, on the other
 * hand, this ensures that each of the currently existing fields is properly mapped. !!!
 * <p>
 * I recommend writing these types of tests at the end of the project if you have excess time.
 *
 * @author janlingen
 */
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
