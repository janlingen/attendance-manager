package com.azakamu.attendencemanager.domain.entities;

import com.azakamu.attendencemanager.domain.values.ExamId;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * {@link Student} is an entity that represents a student participating in the project for which
 * this program is to manage attendance. Each student has an ID, a Github name and a Github ID
 * (since this program will be used for software engineering projects at the university, we will use
 * Github as authenticator), a number of minutes they can use to take vacations, and two lists, one
 * for their {@link Vacation}'s and one for their {@link Exam}'s.
 */
public class Student {

  private final Long id;
  private final String githubName;
  private final String githubId;
  private Long leftoverVacationTime;
  private final List<Vacation> vacationList;
  private final List<ExamId> examIdList;

  /**
   * Required arguments constructor, that initializes every class attribute.
   *
   * @param id                   the id of the student
   * @param githubName           the Github name of the student
   * @param githubId             the unique Github ID of the student
   * @param leftoverVacationTime the minutes of vacation a student can take
   * @param vacationList         the vacations of the student
   * @param examIdList           the references of the student to his exams
   */
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
    calcLeftoverVacationTime();
  }

  /**
   * Calculates the leftover vacation time of the student based on his list of vacations.
   */
  private void calcLeftoverVacationTime() {
    for (Vacation v : this.vacationList) {
      this.leftoverVacationTime -= v.timeframe().duration();
    }
  }

  /**
   * Adds a new {@link Vacation} to {@link Student#vacationList}.
   *
   * @param vacation the vacation to add
   */
  public void addVacation(Vacation vacation) {
    this.vacationList.add(vacation);
    this.leftoverVacationTime -= vacation.timeframe().duration();
  }

  /**
   * Adds multiple new {@link Vacation}s to {@link Student#vacationList}.
   *
   * @param vacations the list of vacations to add
   */
  public void addVacations(Collection<Vacation> vacations) {
    for (Vacation u : vacations) {
      addVacation(u);
    }
  }

  /**
   * Removes a {@link Vacation} from {@link Student#vacationList}.
   *
   * @param vacation the vacation to remove
   */
  public void removeVacation(Vacation vacation) {
    this.vacationList.remove(vacation);
    this.leftoverVacationTime += vacation.timeframe().duration();
  }

  /**
   * Removes multiple {@link Vacation}s from {@link Student#vacationList}.
   *
   * @param vacations the list of vacations to remove
   */
  public void removeVacations(Collection<Vacation> vacations) {
    for (Vacation u : vacations) {
      removeVacation(u);
    }
  }

  /**
   * Adds a new reference as {@link ExamId} to {@link Student#examIdList}.
   *
   * @param examId the exam ID to add
   */
  public void addExamId(ExamId examId) {
    this.examIdList.add(examId);
  }

  /**
   * Removes a {@link ExamId} from {@link Student#examIdList}.
   *
   * @param examId the exam ID to remove
   */
  public void removeExamId(ExamId examId) {
    this.examIdList.remove(examId);
  }

  /**
   * Sorts the {@link Student#vacationList} based on their {@link Timeframe}.
   */
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

  /**
   * Creates a dummy instance of Student.
   *
   * @return an instance of {@link Student} with id -1.
   */
  public static Student createDummy() {
    return new Student(-1L, "githubId-dummy", "githubId-dummy", 300L,
        Collections.emptyList(), Collections.emptyList());
  }

  // Basic Getter
  // ----------------------------------------------------------------------------------------------
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


  // Equals and Hashcode
  // ----------------------------------------------------------------------------------------------
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return id.equals(student.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
