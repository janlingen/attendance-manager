package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import com.azakamu.attendencemanager.domain.entities.Student;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * @author janlingen
 */
@Mapper(
    uses = {
        VacationMapper.class,
        ExamIdMapper.class})
public interface StudentMapper {

  Student toDomain(StudentDto studentDto);

  StudentDto toDto(Student student);

  List<Student> toDomainSet(List<StudentDto> studentDtos);
}
