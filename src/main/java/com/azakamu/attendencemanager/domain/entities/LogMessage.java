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
