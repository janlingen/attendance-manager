package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.LogMessageDto;
import com.azakamu.attendencemanager.domain.entities.LogMessage;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * @author janlingen
 */
@Mapper
public interface LogMessageMapper {

  LogMessage toDomain(LogMessageDto logMessageDto);

  LogMessageDto toDto(LogMessage logMessage);

  List<LogMessage> toDomainList(List<LogMessageDto> logMessageDtos);

}
