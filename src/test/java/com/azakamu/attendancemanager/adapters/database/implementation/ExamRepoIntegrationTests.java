package com.azakamu.attendancemanager.adapters.database.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendancemanager.adapters.database.dataaccess.ExamDao;
import com.azakamu.attendancemanager.application.repositories.ExamRepository;
import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.values.ExamId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * It is debatable if you should save data in the arrangement step with the JPA DataAccessObject or
 * with the save method from your own repository implementation. I've done it both ways in different
 * tests, for educational purposes.
 *
 * @author janlingen
 */
@DataJpaTest
@ActiveProfiles("test")
public class ExamRepoIntegrationTests {

  private final ExamRepository repository;

  public ExamRepoIntegrationTests(@Autowired ExamDao dao) {
    this.repository = new ExamRepoImpl(dao);
  }

  @Test
  @DisplayName("exam is saved correctly, id is generated")
  void saveTest1() {
    // arrange
    Exam exam = Exam.createDummy();

    // act
    Exam result = repository.save(exam);

    // assert
    assertThat(result.getExamId().id()).isNotEqualTo(exam.getExamId().id());
  }

  @Test
  @DisplayName("exam is found based on his id")
  void findByIdTest1() {
    // arrange
    Exam exam = repository.save(Exam.createDummy());

    // act
    Exam result = repository.findById(exam.getExamId());

    // assert
    assertThat(result).isEqualTo(exam);
  }

  @Test
  @DisplayName("exam is not found based on his id, dummy is returned")
  void findByIdTest2() {
    // arrange
    Exam exam = Exam.createDummy();

    // act
    Exam result = repository.findById(exam.getExamId());

    // assert
    assertThat(result).isEqualTo(exam);
  }

  @Test
  @DisplayName("find exams to multiple examIds")
  void findAllByIdTest1() {
    // arrange
    Exam exam1 = repository.save(Exam.createDummy());
    Exam exam2 = repository.save(Exam.createDummy());

    // act
    List<Exam> result = repository.findAllById(List.of(exam1.getExamId(), exam2.getExamId()));

    // assert
    assertThat(result.size()).isEqualTo(2);
    assertThat(result).contains(exam1, exam2);
  }

  @Test
  @DisplayName("find no exam, list is empty")
  void findAllByIdTest2() {
    // arrange
    ExamId examId1 = new ExamId(1L);
    ExamId examId2 = new ExamId(2L);

    // act
    List<Exam> result = repository.findAllById(List.of(examId1, examId2));

    // assert
    assertThat(result.size()).isZero();
    ;
  }

  @Test
  @DisplayName("find all exams")
  void findAllTest1() {
    // arrange
    Exam exam1 = repository.save(Exam.createDummy());
    Exam exam2 = repository.save(Exam.createDummy());

    // act
    List<Exam> result = repository.findAll();

    // assert
    assertThat(result.size()).isEqualTo(2);
    assertThat(result).contains(exam1, exam2);
  }

  @Test
  @DisplayName("find all exams, when there are no exams")
  void findAllTest2() {
    // act
    List<Exam> result = repository.findAll();

    // assert
    assertThat(result.size()).isZero();
  }


}
