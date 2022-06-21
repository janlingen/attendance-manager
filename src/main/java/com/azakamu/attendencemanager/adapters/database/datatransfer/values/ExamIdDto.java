package com.azakamu.attendencemanager.adapters.database.datatransfer.values;

import org.springframework.data.relational.core.mapping.Table;

@Table("EXAM_STUDENT")
public record ExamIdDto(Long id) {

}
