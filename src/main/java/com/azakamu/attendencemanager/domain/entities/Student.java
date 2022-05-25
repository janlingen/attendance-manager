package com.azakamu.attendencemanager.domain.entities;

import com.azakamu.attendencemanager.domain.values.ExamId;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.ArrayList;
import java.util.List;

public class Student {

  private final Long id;
  private final String name;
  private final String github_name;
  private Integer vacationTime;
  private final List<Vacation> vacationList;
  private final List<ExamId> examIdList;

  public Student(Long id, String name, String github_name, List<Vacation> vacationList,
                 List<ExamId> examIdList) {
    this.id = id;
    this.name = name;
    this.github_name = github_name;
    this.vacationList = new ArrayList<>(vacationList);
    this.examIdList = new ArrayList<>(examIdList);
//    this.vacationTime = calculateVacationTime(this.vacationList);
  }
}
