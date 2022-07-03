package com.azakamu.attendancemanager.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.azakamu.attendancemanager.application.repositories.TutorRepository;
import com.azakamu.attendancemanager.domain.entities.Student;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author janlingen
 */
public class TutorServiceUnitTests {

  private final TutorRepository tutorRepo = mock(TutorRepository.class);
  private TutorService service;

  @BeforeEach
  void setup() {
    service = new TutorService(tutorRepo);
  }

  @Test
  @DisplayName("try to get all students as list")
  void getAllStudentsTest1() {
    // arrange
    Student student = Student.createDummy();
    when(tutorRepo.findAll()).thenReturn(List.of(student));

    // act
    List<Student> result = service.getAllStudents();

    // assert
    assertThat(result.size()).isEqualTo(1);
    assertThat(result).contains(student);

  }

  @Test
  @DisplayName("try to get an existing student")
  void getExistingStudentTest1() {
    // arrange
    Student student = Student.createDummy();
    when(tutorRepo.findByGithubId(student.getGithubId())).thenReturn(student);

    // act
    Student result = service.getExistingStudent(student.getGithubId());

    // assert
    assertThat(result).isEqualTo(student);
  }
}
