package com.azakamu.attendancemanager.adapters.database.mapper;

import com.azakamu.attendancemanager.adapters.database.datatransfer.values.VacationDto;
import com.azakamu.attendancemanager.domain.values.Vacation;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author janlingen
 */
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        TimeframeMapper.class})
public interface VacationMapper {

  Vacation toDomain(VacationDto vacationDto);

  VacationDto toDto(Vacation vacation);

  Set<Vacation> toDomainSet(Set<VacationDto> vacationDtos);

  Set<VacationDto> toDtoSet(Set<Vacation> vacations);
}
