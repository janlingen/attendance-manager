package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendencemanager.domain.values.ExamId;
import java.util.Set;
import org.mapstruct.Mapper;

@Mapper
public interface ExamIdMapper {

  ExamId toDomain(ExamIdDto examIdDto);

  ExamIdDto toDto(ExamId examId);

  Set<ExamId> toDomainSet(Set<ExamIdDto> examIdDtos);

  Set<ExamIdDto> toDtoSet(Set<ExamId> examIds);
}
