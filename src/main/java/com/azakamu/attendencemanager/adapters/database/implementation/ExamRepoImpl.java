package com.azakamu.attendencemanager.adapters.database.implementation;

import com.azakamu.attendencemanager.adapters.database.dataaccess.ExamDao;
import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.ExamDto;
import com.azakamu.attendencemanager.adapters.database.mapper.ExamMapper;
import com.azakamu.attendencemanager.application.repositories.ExamRepository;
import com.azakamu.attendencemanager.domain.entities.Exam;
import com.azakamu.attendencemanager.domain.values.ExamId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepoImpl implements ExamRepository {

  private final ExamDao dataaccess;
  private final ExamMapper mapper;

  public ExamRepoImpl(ExamDao dataaccess, ExamMapper mapper) {
    this.dataaccess = dataaccess;
    this.mapper = mapper;
  }

  @Override
  public void save(Exam exam) {
    dataaccess.save(mapper.toDto(exam));
  }

  @Override
  public Exam findById(ExamId examId) {
    return dataaccess.findById(examId.id()).map(mapper::toDomain)
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
    List<ExamDto> exams = new ArrayList<>();
    dataaccess.findAll().forEach(exams::add);
    return exams.stream().map(mapper::toDomain).collect(Collectors.toList());
  }
}
