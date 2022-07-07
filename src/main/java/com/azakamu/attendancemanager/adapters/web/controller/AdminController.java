package com.azakamu.attendancemanager.adapters.web.controller;

import com.azakamu.attendancemanager.application.services.AdminService;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author janlingen
 */
@Secured("ROLE_ADMIN")
@Controller()
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/admin")
  public String admin(Model model) {
    List<LogMessage> logs = adminService.getSortedLogs();
    model.addAttribute("logs", logs);
    return "admin";
  }

}
