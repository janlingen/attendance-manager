package com.azakamu.attendencemanager.adapters.database.datatransfer.entities;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.ExamIdDto;
import com.azakamu.attendencemanager.adapters.database.datatransfer.values.VacationDto;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("STUDENT")
public record StudentDto(@Id Long id, String githubName, String githubId, Long leftoverVacationTime,
                         @MappedCollection(idColumn = "STUDENT_ID") List<VacationDto> vacationList,
                         @MappedCollection(idColumn = "STUDENT_ID") List<ExamIdDto> examIdList) {

}
