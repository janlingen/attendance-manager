package com.azakamu.attendencemanager.database;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.dataaccess.StudentDao;
import com.azakamu.attendencemanager.adapters.database.implementation.StudentRepoImpl;
import com.azakamu.attendencemanager.application.repositories.StudentRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
import com.azakamu.attendencemanager.domain.values.ExamId;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class StudentRepoIntegrationTests {


  private final StudentRepository repository;

  public StudentRepoIntegrationTests(@Autowired StudentDao dao) {
    this.repository = new StudentRepoImpl(dao);
  }

  @Test
  void findByIdTest1() {
    // arrange
    repository.save(new Student(null, "test", "1234", 240L,
        Collections.emptyList(), Collections.emptyList()));

    // act
    Student result = repository.findById(1L);

    // assert
    assertThat(result.getId()).isEqualTo(1L);
  }
}
