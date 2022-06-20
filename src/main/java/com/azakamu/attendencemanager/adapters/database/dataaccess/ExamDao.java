package com.azakamu.attendencemanager.adapters.database.dataaccess;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.ExamDto;
import org.springframework.data.repository.CrudRepository;

public interface ExamDao extends CrudRepository<ExamDto, Long> {}
