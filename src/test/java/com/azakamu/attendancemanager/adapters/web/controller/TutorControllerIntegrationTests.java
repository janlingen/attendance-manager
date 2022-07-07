package com.azakamu.attendancemanager.adapters.web.controller;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.azakamu.attendancemanager.application.services.ExamService;
import com.azakamu.attendancemanager.application.services.TutorService;
import com.azakamu.attendancemanager.domain.entities.Student;
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
@WebMvcTest(controllers = TutorController.class)
@AutoConfigureMockMvc(addFilters = false) // to disable Authentication
public class TutorControllerIntegrationTests {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ExamService examService;

  @MockBean
  TutorService tutorService;

  @Test
  @DisplayName("GET request on /tutor, check view is correct")
  void tutorTest1() throws Exception {
    when(tutorService.getAllStudents()).thenReturn(List.of(Student.createDummy()));

    mockMvc.perform(get("/tutor"))
        .andExpect(view().name("tutor"))
        .andExpect(status().isOk());

    verify(tutorService, times(1)).getAllStudents();
  }

  @Test
  @DisplayName("GET request on /displayexemptions/12345678, check view is correct")
  void displayexemptionsTest1() throws Exception {
    when(tutorService.getExistingStudent("12345678")).thenReturn(Student.createDummy());
    when(examService.getExamsByIds(Student.createDummy().getExamIds())).thenReturn(
        Collections.emptyList());

    mockMvc.perform(get("/display-exemptions/12345678"))
        .andExpect(view().name("display-exemptions"))
        .andExpect(status().isOk());

    verify(tutorService, times(1)).getExistingStudent("12345678");
    verify(examService, times(1)).getExamsByIds(Collections.emptySet());
  }
}
