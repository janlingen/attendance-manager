package com.azakamu.attendancemanager.application.repositories;

import com.azakamu.attendancemanager.domain.entities.Student;
import java.util.List;

/**
 * @author janlingen
 */
public interface TutorRepository {

  List<Student> findAll();

  Student findByGithubId(String githubId);

}
