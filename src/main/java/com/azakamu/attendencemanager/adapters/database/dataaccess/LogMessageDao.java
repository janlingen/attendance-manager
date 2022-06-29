package com.azakamu.attendencemanager.adapters.database.dataaccess;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.LogMessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMessageDao extends JpaRepository<LogMessageDto, Long> {

}
