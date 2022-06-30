package com.azakamu.attendencemanager.application.repositories;

import com.azakamu.attendencemanager.domain.entities.LogMessage;
import java.util.List;

public interface AdminRepository {

  List<LogMessage> findAll();

  LogMessage save(LogMessage message);

}
