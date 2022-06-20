package com.azakamu.attendencemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendencemanager.adapters.database.datatransfer.values.TimeframeDto;

public record ExamDto(ExamIdDto examId, String name, Integer exemptionOffset,
                      TimeframeDto timeframe, Boolean online) {

}
