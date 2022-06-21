package com.azakamu.attendencemanager.adapters.database.dataaccess;

import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.StudentDto;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentDao extends CrudRepository<StudentDto, Long> {

  @Query("SELECT * FROM STUDENT S WHERE S.GITHUB_ID = :githubId")
  Optional<StudentDto> findByGithubId(@Param("githubId") String githubId);

}
