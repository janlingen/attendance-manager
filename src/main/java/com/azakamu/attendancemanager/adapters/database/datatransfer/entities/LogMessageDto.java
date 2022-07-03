package com.azakamu.attendancemanager.adapters.database.datatransfer.entities;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author janlingen
 */
@Entity
public class LogMessageDto {

  @Id
  @GeneratedValue
  private Long id;
  @CreationTimestamp
  private LocalDateTime createdAt;
  private String githubId;
  private String action;

  public LogMessageDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getGithubId() {
    return githubId;
  }

  public void setGithubId(String githubId) {
    this.githubId = githubId;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
