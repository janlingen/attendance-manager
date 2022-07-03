package com.azakamu.attendancemanager.adapters.database.datatransfer.values;

import javax.persistence.Embeddable;

/**
 * @author janlingen
 */
@Embeddable
public class ExamIdDto {

  Long id;

  public ExamIdDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
