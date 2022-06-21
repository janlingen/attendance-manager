package com.azakamu.attendencemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.TimeframeDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("EXAM")
public record ExamDto(@Id Long id, String name, Integer exemptionOffset,
                      TimeframeDto timeframe, Boolean online) {

}
