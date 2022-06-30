package com.azakamu.attendencemanager.domain.entities;

import java.time.LocalDateTime;

public class LogMessage {

  private final String githubId;
  private final String action;
  private final Long id;
  private final LocalDateTime createdAt;


  public LogMessage(String githubId, String action, Long id, LocalDateTime createdAt) {
    this.githubId = githubId;
    this.action = action;
    this.id = id;
    this.createdAt = createdAt;
  }

  public static LogMessage createDummy() {
    return new LogMessage("12345678", "light-saber training", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
  }

  public String getGithubId() {
    return githubId;
  }

  public String getAction() {
    return action;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
