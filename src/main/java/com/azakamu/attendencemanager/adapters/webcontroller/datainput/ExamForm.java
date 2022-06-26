package com.azakamu.attendencemanager.adapters.webcontroller.datainput;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public record ExamForm(
    String name,
    Boolean online,
    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @DateTimeFormat(pattern = "HH:mm") LocalTime start,
    @DateTimeFormat(pattern = "HH:mm") LocalTime end) {

}