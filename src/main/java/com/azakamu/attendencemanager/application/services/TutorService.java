package com.azakamu.attendencemanager.application.services;


import com.azakamu.attendencemanager.application.repositories.TutorRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
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
