package com.azakamu.attendancemanager.application.repositories;

import com.azakamu.attendancemanager.domain.entities.Student;

/**
 * @author janlingen
 */
public interface StudentRepository {

  Student save(Student student);

  Student findById(Long id);

  Student findByGithubId(String githubId);
}
