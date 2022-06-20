package com.azakamu.attendencemanager.adapters.database.dataaccess;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<StudentDto, Long> {

}
