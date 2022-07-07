package com.azakamu.attendancemanager.adapters.web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.azakamu.attendancemanager.application.services.AdminService;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
import java.time.LocalDateTime;
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
@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false) // to disable Authentication
public class AdminControllerIntegrationTests {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  AdminService adminService;


  @Test
  @DisplayName("GET request on /admin, check view is correct")
  void adminTest1() throws Exception {
    when(adminService.getSortedLogs()).thenReturn(
        List.of(new LogMessage("12345678", "light-saber training", -1L,
            LocalDateTime.now())));

    mockMvc
        .perform(get("/admin"))
        .andExpect(view().name("admin"))
        .andExpect(status().isOk());

    verify(adminService, times(1)).getSortedLogs();

  }
}
