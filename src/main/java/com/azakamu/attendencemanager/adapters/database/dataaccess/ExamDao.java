package com.azakamu.attendencemanager.adapters.database.dataaccess;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.ExamDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author janlingen
 */
@Repository
public interface ExamDao extends JpaRepository<ExamDto, Long> {

}
