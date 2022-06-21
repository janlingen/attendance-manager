package com.azakamu.attendencemanager.adapters.database.mapper;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.TimeframeDto;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    implementationName = "DtoTimeframeMapperImpl",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TimeframeMapper {

  Timeframe toDomain(TimeframeDto timeframeDto);

  TimeframeDto toDto(Timeframe timeframe);
}

