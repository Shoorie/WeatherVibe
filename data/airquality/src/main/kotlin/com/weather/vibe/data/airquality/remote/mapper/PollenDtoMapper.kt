package com.weather.vibe.data.airquality.remote.mapper

import com.weather.vibe.data.airquality.remote.dto.CurrentPollenDto
import com.weather.vibe.data.airquality.remote.dto.PollenResponseDto
import com.weather.vibe.domain.airquality.model.Pollen
import com.weather.vibe.domain.airquality.model.PollenLevel
import com.weather.vibe.domain.airquality.model.PollenReading
import com.weather.vibe.domain.airquality.model.PollenSpecies
import com.weather.vibe.domain.airquality.model.PollenSpecies.ALDER
import com.weather.vibe.domain.airquality.model.PollenSpecies.BIRCH
import com.weather.vibe.domain.airquality.model.PollenSpecies.GRASS
import com.weather.vibe.domain.airquality.model.PollenSpecies.MUGWORT
import com.weather.vibe.domain.airquality.model.PollenSpecies.OLIVE
import com.weather.vibe.domain.airquality.model.PollenSpecies.RAGWEED
import com.weather.vibe.domain.weather.model.Coordinates
import org.koin.core.annotation.Factory
import java.time.LocalDateTime

@Factory
internal class PollenDtoMapper {

  fun toDomain(
    response: PollenResponseDto,
    coordinates: Coordinates
  ): Pollen {

    val current = response.current
      ?: error("Pollen response missing current block")

    return Pollen(
      coordinates = coordinates,
      measuredAt = LocalDateTime.parse(current.time),
      readings = current.toReadings()
    )
  }

  private fun CurrentPollenDto.toReadings(): List<PollenReading> =
    listOfNotNull(
      reading(ALDER, alderPollen),
      reading(BIRCH, birchPollen),
      reading(GRASS, grassPollen),
      reading(MUGWORT, mugwortPollen),
      reading(OLIVE, olivePollen),
      reading(RAGWEED, ragweedPollen)
    )

  private fun reading(species: PollenSpecies, grainsPerCubicMetre: Double?): PollenReading? =
    grainsPerCubicMetre?.let {
      PollenReading(
        species = species,
        grainsPerCubicMetre = it,
        level = PollenLevel.from(species, it)
      )
    }
}
