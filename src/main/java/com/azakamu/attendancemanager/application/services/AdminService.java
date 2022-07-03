package com.azakamu.attendancemanager.application.services;

import com.azakamu.attendancemanager.application.repositories.AdminRepository;
import com.azakamu.attendancemanager.domain.entities.LogMessage;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author janlingen
 */
@Service
public class AdminService {

  private final AdminRepository adminRepository;

  public AdminService(AdminRepository adminRepository) {
    this.adminRepository = adminRepository;
  }

  public List<LogMessage> getSortedLogs() {
    List<LogMessage> toSort = adminRepository.findAll();
    sortLogMessages(toSort);
    return toSort;
  }

  public LogMessage save(LogMessage logMessage) {
    return adminRepository.save(logMessage);
  }


  private void sortLogMessages(List<LogMessage> logs) {
    logs.sort((log1, log2) -> {
      if (log1.getCreatedAt().isBefore(log2.getCreatedAt())) {
        return -1;
      } else if (log1.getCreatedAt().isAfter(log2.getCreatedAt())) {
        return 1;
      }
      return 0;
    });
  }
}
