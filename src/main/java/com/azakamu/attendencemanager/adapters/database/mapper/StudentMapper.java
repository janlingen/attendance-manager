package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import com.azakamu.attendencemanager.domain.entities.Student;
import org.mapstruct.Mapper;

@Mapper(
    uses = {
        VacationMapper.class,
        ExamIdMapper.class})
public interface StudentMapper {

  Student toDomain(StudentDto studentDto);

  StudentDto toDto(Student student);
}
