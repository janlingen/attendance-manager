package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendencemanager.domain.values.ExamId;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "DtoExamIdMapperImpl",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ExamIdMapper {
  ExamId toDomain(ExamIdDto examIdDto);

  ExamIdDto toDto(ExamId examId);

  List<ExamId> toDomainList(List<ExamIdDto> examIdDtos);

  List<ExamIdDto> toEntityList(List<ExamId> examIds);

}
