package com.azakamu.attendancemanager.adapters.database.mapper;

import com.azakamu.attendancemanager.adapters.database.datatransfer.entities.LogMessageDto;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
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
