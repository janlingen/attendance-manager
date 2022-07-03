package com.azakamu.attendancemanager.adapters.database.dataaccess;

import com.azakamu.attendancemanager.adapters.database.datatransfer.entities.LogMessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author janlingen
 */
@Repository
public interface LogMessageDao extends JpaRepository<LogMessageDto, Long> {

}
