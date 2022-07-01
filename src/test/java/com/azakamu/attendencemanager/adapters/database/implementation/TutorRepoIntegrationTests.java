package com.azakamu.attendencemanager.adapters.database.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.dataaccess.StudentDao;
import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import com.azakamu.attendencemanager.adapters.database.mapper.StudentMapper;
import com.azakamu.attendencemanager.application.repositories.TutorRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class TutorRepoIntegrationTests {

  private final TutorRepository repository;
  private final StudentDao dao;
  private final StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

  public TutorRepoIntegrationTests(@Autowired StudentDao dao) {
    this.repository = new TutorRepoImpl(dao);
    this.dao = dao;
  }

  @Test
  @DisplayName("try to find all students, returns student correctly")
  void findAllTest1() {
    // arrange
    StudentDto student = dao.save(mapper.toDto(Student.createDummy()));

    // act
    List<Student> result = repository.findAll();

    // assert
    assertThat(result.size()).isNotZero();
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.get(0)).isEqualTo(mapper.toDomain(student));
  }

  @Test
  @DisplayName("student is not found based on his github-id, dummy is returned")
  void findByGithubIdTest1() {
    // arrange
    Student student = Student.createDummy();

    // act
    Student result = repository.findByGithubId(student.getGithubId());

    // assert
    assertThat(result).isEqualTo(student);
  }

  @Test
  @DisplayName("student is found based on his github-id")
  void findByGithubIdTest2() {
    // arrange
    Student student = new Student(null, "skywalker", "12345678",
        Collections.emptySet(), Collections.emptySet());
    StudentDto savedStudent = dao.save(mapper.toDto(student));

    // act
    Student result = repository.findByGithubId(student.getGithubId());

    // assert
    assertThat(result).isEqualTo(mapper.toDomain(savedStudent));
  }
}
