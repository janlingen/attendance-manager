package com.azakamu.attendancemanager.adapters.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendancemanager.adapters.database.datatransfer.entities.LogMessageDto;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
import java.util.List;
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
public class LogMessageMapperUnitTests {

  LogMessageMapper mapper = Mappers.getMapper(LogMessageMapper.class);

  @Test
  void DomainToDtoTest1() {
    // arrange
    LogMessage logMessage = LogMessage.createDummy();

    // act
    LogMessageDto logMessageDto = mapper.toDto(logMessage);

    // assert
    assertThat(logMessageDto.getId()).isEqualTo(logMessage.getId());
    assertThat(logMessageDto.getAction()).isEqualTo(logMessage.getAction());
    assertThat(logMessageDto.getGithubId()).isEqualTo(logMessage.getGithubId());
    assertThat(logMessageDto.getCreatedAt()).isEqualTo(logMessage.getCreatedAt());
  }

  @Test
  void DtoToDomainTest1() {
    // arrange
    LogMessageDto logMessageDto = mapper.toDto(LogMessage.createDummy());

    // act
    LogMessage logMessage = mapper.toDomain(logMessageDto);

    // assert
    assertThat(logMessage.getId()).isEqualTo(logMessageDto.getId());
    assertThat(logMessage.getAction()).isEqualTo(logMessageDto.getAction());
    assertThat(logMessage.getGithubId()).isEqualTo(logMessageDto.getGithubId());
    assertThat(logMessage.getCreatedAt()).isEqualTo(logMessageDto.getCreatedAt());
  }

  @Test
  void DtoListToDomainListTest1() {
    // arrange
    LogMessageDto logMessageDto = mapper.toDto(LogMessage.createDummy());

    // act
    List<LogMessage> domainList = mapper.toDomainList(List.of(logMessageDto));

    // assert
    assertThat(domainList.get(0)).isEqualTo(mapper.toDomain(logMessageDto));
  }

}
