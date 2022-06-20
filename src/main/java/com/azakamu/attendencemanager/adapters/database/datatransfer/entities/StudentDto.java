package com.azakamu.attendencemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendencemanager.adapters.database.datatransfer.values.VacationDto;
import java.util.List;

public record StudentDto(Long id, String githubName, String githubId, Long leftoverVacationTime,
                         List<VacationDto> vacationList, List<ExamIdDto> examIdList) {

}
