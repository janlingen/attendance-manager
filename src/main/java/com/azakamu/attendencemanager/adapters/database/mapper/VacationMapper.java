package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.VacationDto;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    implementationName = "DtoVacationMapperImpl",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        TimeframeMapper.class})
public interface VacationMapper {

  Vacation toDomain(VacationDto vacationDto);

  VacationDto toDto(Vacation vacation);

  List<Vacation> toDomainList(List<VacationDto> vacationDtos);

  List<VacationDto> toEntityList(List<Vacation> vacations);
}
