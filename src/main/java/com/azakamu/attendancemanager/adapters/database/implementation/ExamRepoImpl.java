package com.azakamu.attendancemanager.adapters.database.implementation;

import com.azakamu.attendancemanager.adapters.database.dataaccess.ExamDao;
import com.azakamu.attendancemanager.adapters.database.datatransfer.entities.ExamDto;
import com.azakamu.attendancemanager.adapters.database.mapper.ExamMapper;
import com.azakamu.attendancemanager.application.repositories.ExamRepository;
import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.values.ExamId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @author janlingen
 */
@Component
public class ExamRepoImpl implements ExamRepository {

  private final ExamDao examDao;

  private final ExamMapper mapper;

  public ExamRepoImpl(ExamDao examDao) {
    this.examDao = examDao;
    this.mapper = Mappers.getMapper(ExamMapper.class);
  }

  @Override
  public Exam save(Exam exam) {
    return mapper.toDomain(examDao.save(mapper.toDto(exam)));
  }

  @Override
  public Exam findById(ExamId examId) {
    return examDao.findById(examId.id()).map(mapper::toDomain)
        .orElse(Exam.createDummy());
  }

  @Override
  public List<Exam> findAllById(List<ExamId> examIds) {
    List<Exam> exams = new ArrayList<>();
    for (ExamId examId : examIds) {
      Exam exam = findById(examId);
      if (!exam.getExamId().id().equals(-1L)) {
        exams.add(exam);
      }
    }
    return exams;
  }

  @Override
  public List<Exam> findAll() {
    List<ExamDto> exams = new ArrayList<>(examDao.findAll());
    return exams.stream().map(mapper::toDomain).collect(Collectors.toList());
  }
}
