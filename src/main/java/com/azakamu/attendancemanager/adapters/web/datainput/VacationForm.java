package com.azakamu.attendancemanager.adapters.web.datainput;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author janlingen
 */
public record VacationForm(
    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @DateTimeFormat(pattern = "HH:mm") LocalTime start,
    @DateTimeFormat(pattern = "HH:mm") LocalTime end,
    String reason) {

}
