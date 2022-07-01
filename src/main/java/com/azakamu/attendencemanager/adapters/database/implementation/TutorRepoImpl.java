package com.azakamu.attendencemanager.adapters.database.implementation;

import com.azakamu.attendencemanager.adapters.database.dataaccess.StudentDao;
import com.azakamu.attendencemanager.adapters.database.mapper.StudentMapper;
import com.azakamu.attendencemanager.application.repositories.TutorRepository;
import com.azakamu.attendencemanager.domain.entities.Student;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @author janlingen
 */
@Component
public class TutorRepoImpl implements TutorRepository {

  private final StudentDao studentDao;

  private final StudentMapper mapper;

  public TutorRepoImpl(StudentDao studentDao) {
    this.studentDao = studentDao;
    this.mapper = Mappers.getMapper(StudentMapper.class);
  }

  @Override
  public List<Student> findAll() {
    return mapper.toDomainSet(studentDao.findAll());
  }

  @Override
  public Student findByGithubId(String githubId) {
    return studentDao.findByGithubId(githubId).map(mapper::toDomain).orElse(Student.createDummy());
  }
}
