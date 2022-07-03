package com.azakamu.attendancemanager.application.services;


import com.azakamu.attendancemanager.application.repositories.TutorRepository;
import com.azakamu.attendancemanager.domain.entities.Student;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author janlingen
 */
@Service
public class TutorService {

  private final TutorRepository tutorRepository;

  public TutorService(TutorRepository tutorRepository) {
    this.tutorRepository = tutorRepository;
  }

  public List<Student> getAllStudents() {
    return tutorRepository.findAll();
  }

  public Student getExistingStudent(String githubId) {
    return tutorRepository.findByGithubId(githubId);
  }
}
