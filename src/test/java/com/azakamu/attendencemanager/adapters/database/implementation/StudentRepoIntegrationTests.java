package com.azakamu.attendencemanager.adapters.database.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.dataaccess.StudentDao;
import com.azakamu.attendencemanager.application.repositories.StudentRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class StudentRepoIntegrationTests {


  private final StudentRepository repository;

  public StudentRepoIntegrationTests(@Autowired StudentDao dao) {
    this.repository = new StudentRepoImpl(dao);
  }

  @Test
  @DisplayName("student is saved correctly, id is generated")
  void saveTest1() {
    // arrange
    Student student = Student.createDummy();

    // act
    Student result = repository.save(student);

    // assert
    assertThat(result.getId()).isNotEqualTo(student.getId());
  }


  @Test
  @DisplayName("student is found based on his id")
  void findByIdTest1() {
    // arrange
    Student student = repository.save(Student.createDummy());

    // act
    Student result = repository.findById(student.getId());

    // assert
    assertThat(result).isEqualTo(student);
  }

  @Test
  @DisplayName("student is not found based on his id, dummy is returned")
  void findByIdTest2() {
    // arrange
    Student student = Student.createDummy();

    // act
    Student result = repository.findById(student.getId());

    // assert
    assertThat(result).isEqualTo(student);
  }

  @Test
  @DisplayName("student is found based on his githubId")
  void findByGithubIdTest1() {
    // arrange
    Student student = repository.save(Student.createDummy());

    // act
    Student result = repository.findByGithubId(student.getGithubId());

    // assert
    assertThat(result).isEqualTo(student);
  }

  @Test
  @DisplayName("student is not found based on his githubId, dummy is returned")
  void findByGithubIdTest2() {
    // arrange
    Student student = Student.createDummy();

    // act
    Student result = repository.findByGithubId(student.getGithubId());

    // assert
    assertThat(result).isEqualTo(student);
  }

}
