package com.azakamu.attendancemanager.adapters.database.mapper;

import com.azakamu.attendancemanager.adapters.database.datatransfer.entities.ExamDto;
import com.azakamu.attendancemanager.domain.entities.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author janlingen
 */
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        TimeframeMapper.class,
        ExamIdMapper.class})
public interface ExamMapper {

  @Mapping(source = "id", target = "examId.id")
  Exam toDomain(ExamDto examDto);

  @Mapping(source = "examId.id", target = "id")
  ExamDto toDto(Exam exam);
}
