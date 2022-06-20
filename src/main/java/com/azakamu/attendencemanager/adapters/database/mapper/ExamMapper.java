package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.ExamDto;
import com.azakamu.attendencemanager.domain.entities.Exam;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "DtoExamMapperImpl",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {
        TimeframeMapper.class,
        ExamIdMapper.class
    }
)
public interface ExamMapper {
  Exam toDomain(ExamDto examDto);

  ExamDto toDto(Exam exam);
}
