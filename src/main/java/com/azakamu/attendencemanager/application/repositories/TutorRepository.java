package com.azakamu.attendencemanager.application.repositories;

import com.azakamu.attendencemanager.domain.entities.Student;
import java.util.List;

public interface TutorRepository {

  List<Student> findAll();

  Student findByGithubId(String githubId);

}
