package com.azakamu.attendancemanager.domain.entities;

import com.azakamu.attendancemanager.domain.values.ExamId;
import com.azakamu.attendancemanager.domain.values.Timeframe;
import com.azakamu.attendancemanager.domain.values.Vacation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * {@link Student} is an entity that represents a student participating in the project for which
 * this program is to manage attendance. Each student has an ID, a Github name and a Github ID
 * (since this program will be used for software engineering projects at the university, we will use
 * Github as authenticator), a number of minutes they can use to take vacations, and two lists, one
 * for their {@link Vacation}'s and one for their {@link Exam}'s.
 *
 * @author janlingen
 */
public class Student {

  private final Long id;
  private final String githubName;
  private final String githubId;
  private final Set<Vacation> vacations;
  private final Set<ExamId> examIds;
  private Long aggregatedVacationTime;

  /**
   * Required arguments constructor, that initializes every class attribute.
   *
   * @param id         the id of the student
   * @param githubName the Github name of the student
   * @param githubId   the unique Github ID of the student
   * @param vacations  the vacations of the student
   * @param examIds    the references of the student to his exams
   */
  public Student(Long id, String githubName, String githubId,
      Set<Vacation> vacations,
      Set<ExamId> examIds) {
    this.id = id;
    this.githubName = githubName;
    this.githubId = githubId;
    this.vacations = new HashSet<>(vacations);
    this.examIds = new HashSet<>(examIds);
    this.aggregatedVacationTime = 0L;
    calcAggregatedVacationTime();
  }

  /**
   * Creates a dummy instance of Student.
   *
   * @return an instance of {@link Student} with id -1.
   */
  public static Student createDummy() {
    return new Student(-1L, "githubName-dummy", "githubId-dummy",
        Collections.emptySet(), Collections.emptySet());
  }

  /**
   * Calculates the aggregated vacation time of the student based on his list of vacations.
   */
  private void calcAggregatedVacationTime() {
    this.aggregatedVacationTime = 0L;
    for (Vacation v : this.vacations) {
      this.aggregatedVacationTime += v.timeframe().duration();
    }
  }

  /**
   * Adds a new {@link Vacation} to {@link Student#vacations}.
   *
   * @param vacation the vacation to add
   */
  public void addVacation(Vacation vacation) {
    this.vacations.add(vacation);
    this.aggregatedVacationTime += vacation.timeframe().duration();
  }

  /**
   * Adds multiple new {@link Vacation}s to {@link Student#vacations}.
   *
   * @param vacations the list of vacations to add
   */
  public void addVacations(Collection<Vacation> vacations) {
    for (Vacation u : vacations) {
      addVacation(u);
    }
  }

  /**
   * Removes a {@link Vacation} from {@link Student#vacations}.
   *
   * @param vacation the vacation to remove
   */
  public void removeVacation(Vacation vacation) {
    this.vacations.remove(vacation);
    this.aggregatedVacationTime -= vacation.timeframe().duration();
  }

  /**
   * Removes multiple {@link Vacation}s from {@link Student#vacations}.
   *
   * @param vacations the list of vacations to remove
   */
  public void removeVacations(Collection<Vacation> vacations) {
    for (Vacation u : vacations) {
      removeVacation(u);
    }
  }

  /**
   * Adds a new reference as {@link ExamId} to {@link Student#examIds}.
   *
   * @param examId the exam ID to add
   */
  public void addExamId(ExamId examId) {
    this.examIds.add(examId);
  }

  /**
   * Removes a {@link ExamId} from {@link Student#examIds}.
   *
   * @param examId the exam ID to remove
   */
  public void removeExamId(ExamId examId) {
    this.examIds.remove(examId);
  }

  /**
   * Sorts the {@link Student#vacations} based on their {@link Timeframe}.
   */
  private void sortVacations(List<Vacation> vacations) {
    vacations.sort((vacation1, vacation2) -> {
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

  public Long getAggregatedVacationTime() {
    return aggregatedVacationTime;
  }

  public List<Vacation> getVacations() {
    List<Vacation> vacations = new ArrayList<>(this.vacations);
    sortVacations(vacations);
    return vacations;
  }

  public Set<ExamId> getExamIds() {
    return examIds;
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
