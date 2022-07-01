package com.azakamu.attendencemanager.adapters.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import com.azakamu.attendencemanager.domain.entities.Student;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author janlingen
 */
public class StudentMapperUnitTests {

  StudentMapper mapper = Mappers.getMapper(StudentMapper.class);
  VacationMapper vacationMapper = Mappers.getMapper(VacationMapper.class);
  ExamIdMapper examIdMapper = Mappers.getMapper(ExamIdMapper.class);

  @Test
  void DomainToDtoTest1() {
    // arrange
    Student student = Student.createDummy();

    // act
    StudentDto studentDto = mapper.toDto(student);

    // assert
    assertThat(studentDto.getId()).isEqualTo(student.getId());
    assertThat(studentDto.getGithubName()).isEqualTo(student.getGithubName());
    assertThat(studentDto.getGithubId()).isEqualTo(student.getGithubId());
    assertThat(vacationMapper.toDomainSet(studentDto.getVacations())).containsAll(
        student.getVacations());
    assertThat(examIdMapper.toDomainSet(studentDto.getExamIds())).containsAll(
        student.getExamIds());
  }

  @Test
  void DtoToDomainTest1() {
    // arrange
    StudentDto studentDto = mapper.toDto(Student.createDummy());

    // act
    Student student = mapper.toDomain(studentDto);

    // assert
    assertThat(student).isEqualTo(Student.createDummy());
  }
}
