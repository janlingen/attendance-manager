package com.azakamu.attendencemanager.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import com.azakamu.attendencemanager.adapters.database.mapper.StudentMapper;
import com.azakamu.attendencemanager.domain.entities.Student;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class StudentMapperTest {

  StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

  @Test
  void test1() {
    // arrange
    Student student = Student.createDummy();

    // act
    StudentDto studentDto = mapper.toDto(student);

    // assert
    assertThat(studentDto.getGithubName()).isEqualTo(student.getGithubName());
    assertThat(studentDto.getGithubId()).isEqualTo(student.getGithubId());
    assertThat(studentDto.getId()).isEqualTo(student.getId());
  }
}
