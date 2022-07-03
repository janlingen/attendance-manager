package com.azakamu.attendancemanager.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * {@link LogMessage} is an entity that represents a documentation fragment for an action taken by a
 * {@link Student} entity within attendance-manager. {@link LogMessage#getGithubId()} represents a
 * {@link Student#githubId}, {@link LogMessage#action} represents the action taken,
 * {@link LogMessage#id} represents the unique id to distinguish between to logMessages,
 * {@link LogMessage#createdAt} represents the timestamp and action took place.
 *
 * @author janlingen
 */
public class LogMessage {

  private final Long id;
  private final String githubId;
  private final String action;
  private final LocalDateTime createdAt;

  /**
   * Required arguments constructor, that initializes every class attribute.
   *
   * @param githubId  the associated GitHub-Id of a {@link Student}
   * @param action    the action caused
   * @param id        the id of the logMessage
   * @param createdAt the {@link LocalDateTime#now()} timestamp
   */
  public LogMessage(String githubId, String action, Long id, LocalDateTime createdAt) {
    this.githubId = githubId;
    this.action = action;
    this.id = id;
    this.createdAt = createdAt;
  }


  /**
   * Creates a dummy instance of LogMessage.
   *
   * @return an instance of {@link LogMessage} with id -1.
   */
  public static LogMessage createDummy() {
    return new LogMessage("12345678", "light-saber training", -1L,
        LocalDateTime.of(2022, 12, 24, 10, 10));
  }

  // Basic Getter
  // ----------------------------------------------------------------------------------------------

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

  // Equals and Hashcode
  // ----------------------------------------------------------------------------------------------

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogMessage that = (LogMessage) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
