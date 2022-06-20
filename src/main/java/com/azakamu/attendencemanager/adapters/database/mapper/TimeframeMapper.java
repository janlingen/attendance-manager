package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.TimeframeDto;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import java.time.LocalDate;
import java.time.LocalTime;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "DtoTimeframeMapperImpl",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TimeframeMapper {

  Timeframe toDomain(TimeframeDto timeframeDto);

  TimeframeDto toDto(Timeframe timeframe);
}

