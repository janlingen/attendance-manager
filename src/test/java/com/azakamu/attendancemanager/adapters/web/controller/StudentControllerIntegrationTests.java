package com.azakamu.attendancemanager.adapters.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.azakamu.attendancemanager.application.services.ExamService;
import com.azakamu.attendancemanager.application.services.StudentService;
import com.azakamu.attendancemanager.application.services.helper.TimeService;
import com.azakamu.attendancemanager.application.validators.ExamValidator;
import com.azakamu.attendancemanager.application.validators.VacationValidator;
import com.azakamu.attendancemanager.domain.entities.Student;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * !!! Debatable if these tests are real integration tests, for this project everything which needs
 * spring to boot up parts of the application is counted as integration test. !!!
 *
 * @author janlingen
 */
@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false) // to disable Authentication
public class StudentControllerIntegrationTests {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  TimeService timeService;
  @MockBean
  ExamService examService;
  @MockBean
  StudentService studentService;

  @Test
  @DisplayName("GET request on /, check if redirected correctly")
  void indexTest1() throws Exception {
    mockMvc
        .perform(get("/")
            .flashAttr("githubId", "12345678")
            .flashAttr("githubName", "skywalker"))
        .andExpect(view().name("redirect:/student"))
        .andExpect(status().is(302));
  }

  @Test
  @DisplayName("GET request on /student, check if view and method calls are correct ")
  void studentTest1() throws Exception {
    Student student = Student.createDummy();
    when(studentService.getStudent("skywalker", "12345678")).thenReturn(student);
    when(examService.getExamsByIds(any())).thenReturn(Collections.emptyList());

    mockMvc
        .perform(
            get("/student")
                .flashAttr("githubId", "12345678")
                .flashAttr("githubName", "skywalker"))
        .andExpect(view().name("student"))
        .andExpect(status().isOk());

    verify(studentService, times(1)).getStudent("skywalker", "12345678");
    verify(examService, times(1)).getExamsByIds(Collections.emptySet());
    verify(timeService, times(1)).getVacationTime();
  }

  @Test
  @DisplayName("GET request on /vacationenrollment, check if view is correct when form correct")
  void vacationEnrollmentTest1() throws Exception {
    mockMvc
        .perform(
            get("/vacation-enrollment"))
        .andExpect(view().name("vacation-enrollment"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("GET request on /vacationenrollment, check if view is correct when form not correct")
  void vacationEnrollmentTest2() throws Exception {
    mockMvc
        .perform(
            get("/vacation-enrollment")
                .flashAttr("result", VacationValidator.ON_WEEKEND))
        .andExpect(view().name("vacation-enrollment"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("GET request on /examenrollment, check if view is correct when form correct")
  void examEnrollmentTest1() throws Exception {
    mockMvc
        .perform(
            get("/exam-enrollment"))
        .andExpect(view().name("exam-enrollment"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("GET request on /examcreation, check if view is correct when form not correct")
  void examCreationTest1() throws Exception {
    when(timeService.getTimefractions()).thenReturn(List.of("08:30", "08:45", "09:00"));

    mockMvc
        .perform(
            get("/exam-creation")
                .flashAttr("result", ExamValidator.ALREADY_EXISTS))
        .andExpect(view().name("exam-creation"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("GET request on /examcreation, check if view is correct")
  void examCreationTest2() throws Exception {
    when(timeService.getTimefractions()).thenReturn(List.of("08:30", "08:45", "09:00"));

    mockMvc
        .perform(
            get("/exam-creation"))
        .andExpect(view().name("exam-creation"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName(
      "POST request on /cancelVacation, check if redirected correctly")
  void cancelVacationTest1() throws Exception {
    Student student = Student.createDummy();
    LocalDate date = LocalDate.of(2022, 12, 24);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);
    String reason = "light-saber training";

    mockMvc
        .perform(
            post("/cancelVacation")
                .param("date", date.toString())
                .param("start", start.toString())
                .param("end", end.toString())
                .param("reason", reason)
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/"))
        .andExpect(status().is(302));

    verify(studentService, times(1))
        .cancelVacation(student.getGithubName(), student.getGithubId(), date, start, end, reason);
  }

  @Test
  @DisplayName(
      "POST request on /cancelExam, check if redirected correctly")
  void cancelExamTest1() throws Exception {
    Student student = Student.createDummy();

    mockMvc
        .perform(
            post("/cancelExam")
                .param("exam", "1")
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/"))
        .andExpect(status().is(302));

    verify(studentService, times(1))
        .cancelExam(student.getGithubName(), student.getGithubId(), 1L);
  }

  @Test
  @DisplayName("POST request on /enrollVacation, check if redirected correctly when not successful")
  void enrollVacationTest1() throws Exception {
    Student student = Student.createDummy();
    LocalDate date = LocalDate.of(2022, 12, 24);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);
    String reason = "light-saber training";

    mockMvc
        .perform(
            post("/enrollVacation")
                .param("date", date.toString())
                .param("start", start.toString())
                .param("end", end.toString())
                .param("reason", reason)
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/vacation-enrollment"))
        .andExpect(status().is(302));

    verify(studentService, times(1))
        .enrollVacation(student.getGithubName(), student.getGithubId(), date, start, end, reason);
  }

  @Test
  @DisplayName("POST request on /enrollVacation, check if redirected correctly when successful")
  void enrollVacationTest2() throws Exception {
    Student student = Student.createDummy();
    LocalDate date = LocalDate.of(2022, 12, 24);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);
    String reason = "light-saber training";
    when(studentService.enrollVacation(student.getGithubName(), student.getGithubId(), date, start,
        end, reason)).thenReturn(
        VacationValidator.SUCCESS);

    mockMvc
        .perform(
            post("/enrollVacation")
                .param("date", date.toString())
                .param("start", start.toString())
                .param("end", end.toString())
                .param("reason", reason)
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/"))
        .andExpect(status().is(302));

    verify(studentService, times(1))
        .enrollVacation(student.getGithubName(), student.getGithubId(), date, start, end, reason);
  }

  @Test
  @DisplayName("POST request on /enrollExam, check if redirected correctly")
  void enrollExamTest1() throws Exception {
    Student student = Student.createDummy();

    mockMvc
        .perform(
            post("/enrollExam")
                .param("examid", "1")
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/"))
        .andExpect(status().is(302));

    verify(studentService, times(1))
        .enrollExam(student.getGithubName(), student.getGithubId(), 1L);
  }

  @Test
  @DisplayName("POST request on /createExam, check if redirected correctly when not successful")
  void createExamTest1() throws Exception {
    Student student = Student.createDummy();
    String name = "Defense Against the Dark Arts";
    Boolean online = true;
    LocalDate date = LocalDate.of(2022, 12, 24);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);

    mockMvc
        .perform(
            post("/createExam")
                .param("name", name)
                .param("onlineExam", Boolean.toString(online))
                .param("date", date.toString())
                .param("start", start.toString())
                .param("end", end.toString())
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/exam-creation"))
        .andExpect(status().is(302));
    verify(examService, times(1))
        .createExam(name, date, start, end, online);
  }

  @Test
  @DisplayName("POST request on /createExam, check if redirected correctly when successful")
  void createExamTest2() throws Exception {
    Student student = Student.createDummy();
    String name = "Defense Against the Dark Arts";
    LocalDate date = LocalDate.of(2022, 12, 24);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(10, 0);
    when(examService.createExam(name, date, start, end, false)).thenReturn(ExamValidator.SUCCESS);

    mockMvc
        .perform(
            post("/createExam")
                .param("name", name)
                .param("date", date.toString())
                .param("start", start.toString())
                .param("end", end.toString())
                .flashAttr("githubId", student.getGithubId())
                .flashAttr("githubName", student.getGithubName()))
        .andExpect(view().name("redirect:/exam-enrollment"))
        .andExpect(status().is(302));
    verify(examService, times(1))
        .createExam(name, date, start, end, false);
  }

}
