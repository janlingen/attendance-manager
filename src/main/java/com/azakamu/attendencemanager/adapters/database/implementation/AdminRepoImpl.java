package com.azakamu.attendencemanager.adapters.database.implementation;

import com.azakamu.attendencemanager.adapters.database.dataaccess.LogMessageDao;
import com.azakamu.attendencemanager.adapters.database.mapper.LogMessageMapper;
import com.azakamu.attendencemanager.application.repositories.AdminRepository;
import com.azakamu.attendencemanager.domain.entities.LogMessage;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class AdminRepoImpl implements AdminRepository {

  private final LogMessageDao logMessageDao;
  private final LogMessageMapper logMessageMapper;


  public AdminRepoImpl(LogMessageDao logMessageDao) {
    this.logMessageDao = logMessageDao;
    this.logMessageMapper = Mappers.getMapper(LogMessageMapper.class);
  }

  @Override
  public List<LogMessage> findAll() {
    return logMessageMapper.toDomainList(logMessageDao.findAll());
  }

  @Override
  public LogMessage save(LogMessage message) {
    return logMessageMapper.toDomain(logMessageDao.save(logMessageMapper.toDto(message)));
  }


}
