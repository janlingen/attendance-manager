package com.azakamu.attendancemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendancemanager.adapters.database.datatransfer.values.TimeframeDto;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author janlingen
 */
@Entity
public class ExamDto {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private Integer exemptionOffset;
  @Embedded
  private TimeframeDto timeframe;
  private Boolean online;


  public ExamDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getExemptionOffset() {
    return exemptionOffset;
  }

  public void setExemptionOffset(Integer exemptionOffset) {
    this.exemptionOffset = exemptionOffset;
  }

  public TimeframeDto getTimeframe() {
    return timeframe;
  }

  public void setTimeframe(
      TimeframeDto timeframe) {
    this.timeframe = timeframe;
  }

  public Boolean getOnline() {
    return online;
  }

  public void setOnline(Boolean online) {
    this.online = online;
  }
}
