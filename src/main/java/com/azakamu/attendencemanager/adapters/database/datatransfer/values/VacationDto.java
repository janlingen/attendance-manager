package com.azakamu.attendencemanager.adapters.database.datatransfer.values;

import org.springframework.data.relational.core.mapping.Table;

@Table("VACATION")
public record VacationDto(TimeframeDto timeframe, String reason) {

}
