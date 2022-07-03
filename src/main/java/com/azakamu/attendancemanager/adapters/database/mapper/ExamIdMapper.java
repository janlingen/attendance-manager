package com.azakamu.attendancemanager.adapters.database.mapper;

import com.azakamu.attendancemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendancemanager.domain.values.ExamId;
import java.util.Set;
import org.mapstruct.Mapper;

/**
 * @author janlingen
 */
@Mapper
public interface ExamIdMapper {

  ExamId toDomain(ExamIdDto examIdDto);

  ExamIdDto toDto(ExamId examId);

  Set<ExamId> toDomainSet(Set<ExamIdDto> examIdDtos);

  Set<ExamIdDto> toDtoSet(Set<ExamId> examIds);
}
