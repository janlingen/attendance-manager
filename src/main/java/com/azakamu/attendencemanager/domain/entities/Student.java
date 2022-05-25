package com.azakamu.attendencemanager.domain.entities;

import com.azakamu.attendencemanager.domain.values.ExamId;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Student {

  private final Long id;
  private final String githubName;
  private final String githubId;
  private Long leftoverVacationTime;
  private final List<Vacation> vacationList;
  private final List<ExamId> examIdList;

  public Student(Long id, String githubName, String githubId, Long leftoverVacationTime,
                 List<Vacation> vacationList,
                 List<ExamId> examIdList) {
    this.id = id;
    this.githubName = githubName;
    this.githubId = githubId;
    this.vacationList = new ArrayList<>(vacationList);
    sortVacations();
    this.examIdList = new ArrayList<>(examIdList);
    this.leftoverVacationTime = leftoverVacationTime;
    calcLeftoverVacationTime(this.vacationList);
  }

  public Student(Long id, String githubName, String githubId, Long leftoverVacationTime) {
    this.id = id;
    this.githubName = githubName;
    this.githubId = githubId;
    this.vacationList = new ArrayList<>();
    this.examIdList = new ArrayList<>();
    this.leftoverVacationTime = leftoverVacationTime;
    calcLeftoverVacationTime(this.vacationList);
  }

  private void calcLeftoverVacationTime(Collection<Vacation> vacations) {
    for (Vacation v : vacations) {
      this.leftoverVacationTime -= v.timeframe().duration();
    }
  }

  public void addVacation(Vacation vacation) {
    this.vacationList.add(vacation);
    this.leftoverVacationTime -= vacation.timeframe().duration();
  }

  public void addVacations(Collection<Vacation> vacations) {
    for (Vacation u : vacations) {
      addVacation(u);
    }
  }

  public void removeVacation(Vacation vacation) {
    this.vacationList.remove(vacation);
    this.leftoverVacationTime += vacation.timeframe().duration();
  }

  public void removeVacations(Collection<Vacation> vacations) {
    for (Vacation u : vacations) {
      removeVacation(u);
    }
  }

  public void addExamId(ExamId examId) {
    this.examIdList.add(examId);
  }

  public void removeExamId(ExamId examId) {
    this.examIdList.remove(examId);
  }

  private void sortVacations() {
    this.vacationList.sort((vacation1, vacation2) -> {
      if (vacation1.timeframe()
          .date()
          .atTime(vacation1.timeframe().start())
          .isBefore(vacation2.timeframe().date().atTime(vacation2.timeframe().start()))) {
        return -1; // vacation1 < vacation2
      } else if (vacation1.timeframe()
          .date()
          .atTime(vacation1.timeframe().start())
          .isAfter(vacation2.timeframe().date().atTime(vacation2.timeframe().start()))) {
        return 1; // vacation1 > vacation2
      }
      return 0; // vacation1 == vacation2
    });
  }

  public static Student createDummy() {
    return new Student(-1L, "githubId", "githubId", 300L);
  }

  public Long getId() {
    return id;
  }

  public String getGithubName() {
    return githubName;
  }

  public String getGithubId() {
    return githubId;
  }

  public Long getLeftoverVacationTime() {
    return leftoverVacationTime;
  }

  public List<Vacation> getVacationList() {
    sortVacations();
    return vacationList;
  }

  public List<ExamId> getExamIdList() {
    return examIdList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return id.equals(student.id) && githubId.equals(student.githubId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, githubId);
  }
}
