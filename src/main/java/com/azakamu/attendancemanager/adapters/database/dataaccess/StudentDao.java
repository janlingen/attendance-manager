package com.azakamu.attendancemanager.adapters.database.dataaccess;

import com.azakamu.attendancemanager.adapters.database.datatransfer.entities.StudentDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author janlingen
 */
@Repository
public interface StudentDao extends JpaRepository<StudentDto, Long> {

  Optional<StudentDto> findByGithubId(String githubId);

}
