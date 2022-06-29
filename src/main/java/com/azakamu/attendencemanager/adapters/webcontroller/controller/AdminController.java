package com.azakamu.attendencemanager.adapters.webcontroller.controller;

import com.azakamu.attendencemanager.application.services.helper.AdminService;
import com.azakamu.attendencemanager.domain.entities.LogMessage;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Secured("ROLE_ADMIN")
@Controller
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/admin")
  public String organisation(Model model) {
    List<LogMessage> logs = adminService.getSortedLogs();
    model.addAttribute("logs", logs);
    return "admin";
  }

}
