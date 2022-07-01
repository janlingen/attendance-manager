package com.azakamu.attendencemanager.adapters.database.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.adapters.database.datatransfer.values.VacationDto;
import com.azakamu.attendencemanager.domain.values.Vacation;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author janlingen
 */
public class VacationMapperUnitTests {

  VacationMapper mapper = Mappers.getMapper(VacationMapper.class);

  @Test
  void DomainToDtoTest1() {
    // arrange
    Vacation vacation = Vacation.createDummy();

    // act
    VacationDto vacationDto = mapper.toDto(vacation);

    // assert
    assertThat(vacationDto.getTimeframe().getDate()).isEqualTo(vacation.timeframe().date());
    assertThat(vacationDto.getTimeframe().getStart()).isEqualTo(vacation.timeframe().start());
    assertThat(vacationDto.getTimeframe().getEnd()).isEqualTo(vacation.timeframe().end());
    assertThat(vacationDto.getReason()).isEqualTo(vacation.reason());
  }

  @Test
  void DtoToDomainTest1() {
    // arrange
    VacationDto vacationDto = mapper.toDto(Vacation.createDummy());

    // act
    Vacation vacation = mapper.toDomain(vacationDto);

    // assert
    assertThat(vacation.timeframe().date()).isEqualTo(vacationDto.getTimeframe().getDate());
    assertThat(vacation.timeframe().start()).isEqualTo(vacationDto.getTimeframe().getStart());
    assertThat(vacation.timeframe().end()).isEqualTo(vacationDto.getTimeframe().getEnd());
    assertThat(vacation.reason()).isEqualTo(vacationDto.getReason());

  }

  @Test
  void DomainSetToDtoSetTest1() {
    // arrange
    Set<Vacation> domainSet = Set.of(Vacation.createDummy());

    // act
    Set<VacationDto> dtoSet = mapper.toDtoSet(domainSet);

    // assert
    Vacation vacationResult = domainSet.stream().toList().get(0);
    VacationDto vacationDtoResult = dtoSet.stream().toList().get(0);
    assertThat(vacationDtoResult.getTimeframe().getDate()).isEqualTo(
        vacationResult.timeframe().date());
    assertThat(vacationDtoResult.getTimeframe().getStart()).isEqualTo(
        vacationResult.timeframe().start());
    assertThat(vacationDtoResult.getTimeframe().getEnd()).isEqualTo(
        vacationResult.timeframe().end());
    assertThat(vacationDtoResult.getReason()).isEqualTo(vacationResult.reason());
  }


  @Test
  void DtoSetToDomainSetTest1() {
    // arrange
    Set<VacationDto> dtoSet = Set.of(mapper.toDto(Vacation.createDummy()));

    // act
    Set<Vacation> domainSet = mapper.toDomainSet(dtoSet);

    // assert
    Vacation vacationResult = domainSet.stream().toList().get(0);
    VacationDto vacationDtoResult = dtoSet.stream().toList().get(0);
    assertThat(vacationResult.timeframe().date()).isEqualTo(
        vacationDtoResult.getTimeframe().getDate());
    assertThat(vacationResult.timeframe().start()).isEqualTo(
        vacationDtoResult.getTimeframe().getStart());
    assertThat(vacationResult.timeframe().end()).isEqualTo(
        vacationDtoResult.getTimeframe().getEnd());
    assertThat(vacationResult.reason()).isEqualTo(vacationDtoResult.getReason());
  }

}
