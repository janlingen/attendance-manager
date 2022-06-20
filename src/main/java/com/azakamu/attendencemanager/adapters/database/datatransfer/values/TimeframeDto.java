package com.azakamu.attendencemanager.adapters.database.datatransfer.values;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeframeDto(LocalDate date, LocalTime start, LocalTime end) {

}
