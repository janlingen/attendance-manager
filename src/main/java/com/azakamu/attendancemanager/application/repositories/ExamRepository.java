package com.azakamu.attendancemanager.application.repositories;

import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.values.ExamId;
import java.util.List;

/**
 * @author janlingen
 */
public interface ExamRepository {

  Exam save(Exam exam);

  Exam findById(ExamId examId);

  List<Exam> findAllById(List<ExamId> examIds);

  List<Exam> findAll();
}
