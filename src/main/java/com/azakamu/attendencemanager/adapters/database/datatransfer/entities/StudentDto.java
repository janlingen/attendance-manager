package com.azakamu.attendencemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendencemanager.adapters.database.datatransfer.values.VacationDto;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Student")
public class StudentDto {

  @Id
  @GeneratedValue
  private Long id;
  private String githubName;
  private String githubId;
  private Long leftoverVacationTime;
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<VacationDto> vacations;
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<ExamIdDto> examIds;

  public StudentDto(Long id, String githubName, String githubId, Long leftoverVacationTime,
      Set<VacationDto> vacations, Set<ExamIdDto> examIds) {
    this.id = id;
    this.githubName = githubName;
    this.githubId = githubId;
    this.leftoverVacationTime = leftoverVacationTime;
    this.vacations = vacations;
    this.examIds = examIds;
  }

  public StudentDto() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGithubName() {
    return githubName;
  }

  public void setGithubName(String githubName) {
    this.githubName = githubName;
  }

  public String getGithubId() {
    return githubId;
  }

  public void setGithubId(String githubId) {
    this.githubId = githubId;
  }

  public Long getLeftoverVacationTime() {
    return leftoverVacationTime;
  }

  public void setLeftoverVacationTime(Long leftoverVacationTime) {
    this.leftoverVacationTime = leftoverVacationTime;
  }

  public Set<VacationDto> getVacations() {
    return vacations;
  }

  public void setVacations(
      Set<VacationDto> vacations) {
    this.vacations = vacations;
  }

  public Set<ExamIdDto> getExamIds() {
    return examIds;
  }

  public void setExamIds(
      Set<ExamIdDto> examIds) {
    this.examIds = examIds;
  }

  @Override
  public String toString() {
    return "StudentDto{" +
        "id=" + id +
        ", githubName='" + githubName + '\'' +
        ", githubId='" + githubId + '\'' +
        ", leftoverVacationTime=" + leftoverVacationTime +
        ", vacationList=" + vacations +
        ", examIdList=" + examIds +
        '}';
  }
}
