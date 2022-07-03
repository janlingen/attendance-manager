package com.azakamu.attendancemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendancemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendancemanager.adapters.database.datatransfer.values.VacationDto;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author janlingen
 */
@Entity
public class StudentDto {

  @Id
  @GeneratedValue
  private Long id;
  private String githubName;
  private String githubId;
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<VacationDto> vacations;
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<ExamIdDto> examIds;

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
}
