package com.azakamu.attendencemanager.application.repositories;

import com.azakamu.attendencemanager.domain.entities.Student;
import java.util.List;

/**
 * @author janlingen
 */
public interface TutorRepository {

  List<Student> findAll();

  Student findByGithubId(String githubId);

}
