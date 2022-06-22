package com.azakamu.attendencemanager.adapters.database.implementation;

import com.azakamu.attendencemanager.adapters.database.dataaccess.StudentDao;
import com.azakamu.attendencemanager.adapters.database.mapper.StudentMapper;
import com.azakamu.attendencemanager.application.repositories.StudentRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class StudentRepoImpl implements StudentRepository {

  private final StudentDao studentDao;

  private final StudentMapper mapper;

  public StudentRepoImpl(StudentDao studentDao) {
    this.studentDao = studentDao;
    this.mapper = Mappers.getMapper(StudentMapper.class);
  }

  @Override
  public Student save(Student student) {
    return mapper.toDomain(studentDao.save(mapper.toDto(student)));
  }

  @Override
  public Student findById(Long id) {
    return studentDao.findById(id).map(mapper::toDomain).orElse(Student.createDummy());
  }

  @Override
  public Student findByGithubId(String githubId) {
    return studentDao.findByGithubId(githubId).map(mapper::toDomain).orElse(Student.createDummy());
  }
}
