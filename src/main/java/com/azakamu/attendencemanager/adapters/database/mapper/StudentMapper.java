package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import com.azakamu.attendencemanager.adapters.database.datatransfer.values.TimeframeDto;
import com.azakamu.attendencemanager.domain.entities.Student;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "DtoStudentMapperImpl",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {
        VacationMapper.class,
        ExamIdMapper.class
    }
)
public interface StudentMapper {

  Student toDomain(StudentDto studentDto);

  StudentDto toDto(Student student);
}
