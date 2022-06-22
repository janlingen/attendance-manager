package com.azakamu.attendencemanager.application.repositories;

import com.azakamu.attendencemanager.domain.entities.Student;

public interface StudentRepository {

  Student save(Student student);

  Student findById(Long id);

  Student findByGithubId(String githubId);
}
