package com.azakamu.attendencemanager.adapters.database.implementation;


import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.dataaccess.LogMessageDao;
import com.azakamu.attendencemanager.adapters.database.datatransfer.entities.LogMessageDto;
import com.azakamu.attendencemanager.adapters.database.mapper.LogMessageMapper;
import com.azakamu.attendencemanager.application.repositories.AdminRepository;
import com.azakamu.attendencemanager.domain.entities.LogMessage;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author janlingen
 */
@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class AdminRepoIntegrationTests {

  private final AdminRepository repository;
  private final LogMessageDao dao;
  private final LogMessageMapper mapper = Mappers.getMapper(LogMessageMapper.class);


  public AdminRepoIntegrationTests(@Autowired LogMessageDao dao) {
    this.repository = new AdminRepoImpl(dao);
    this.dao = dao;
  }

  @Test
  @DisplayName("logMessage is saved correctly, id is generated")
  void saveTest1() {
    // arrange
    LogMessage logMessage = LogMessage.createDummy();

    // act
    LogMessage result = repository.save(logMessage);

    // assert
    assertThat(result.getId()).isNotEqualTo(logMessage.getId());
  }

  @Test
  @DisplayName("try to find all students, returns student correctly")
  void findAllTest1() {
    // arrange
    LogMessageDto logMessage = dao.save(mapper.toDto(LogMessage.createDummy()));

    // act
    List<LogMessage> result = repository.findAll();

    // assert
    assertThat(result.size()).isNotZero();
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.get(0)).isEqualTo(mapper.toDomain(logMessage));
  }
}
