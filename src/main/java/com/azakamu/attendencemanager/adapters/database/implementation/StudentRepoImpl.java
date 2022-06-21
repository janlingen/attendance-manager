package com.azakamu.attendencemanager.adapters.database.implementation;

import com.azakamu.attendencemanager.adapters.database.dataaccess.StudentDao;
import com.azakamu.attendencemanager.adapters.database.mapper.StudentMapper;
import com.azakamu.attendencemanager.application.repositories.StudentRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepoImpl implements StudentRepository {

  private final StudentDao dataaccess;
  private final StudentMapper mapper;

  public StudentRepoImpl(StudentDao dataaccess, StudentMapper mapper) {
    this.dataaccess = dataaccess;
    this.mapper = mapper;
  }

  @Override
  public void save(Student student) {
    dataaccess.save(mapper.toDto(student));
  }

  @Override
  public Student findById(Long id) {
    return dataaccess.findById(id).map(mapper::toDomain).orElse(Student.createDummy());
  }

  @Override
  public Student findByGithubId(String githubId) {
    return dataaccess.findByGithubId(githubId).map(mapper::toDomain).orElse(Student.createDummy());
  }
}
