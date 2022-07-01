package com.azakamu.attendencemanager.application.repositories;

import com.azakamu.attendencemanager.domain.entities.LogMessage;
import java.util.List;

/**
 * @author janlingen
 */
public interface AdminRepository {

  List<LogMessage> findAll();

  LogMessage save(LogMessage message);

}
