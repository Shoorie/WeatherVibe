package com.weather.vibe.data.airquality.remote.mapper

import com.weather.vibe.data.airquality.remote.dto.AirQualityResponseDto
import com.weather.vibe.domain.airquality.model.AirQuality
import com.weather.vibe.domain.weather.model.Coordinates
import org.koin.core.annotation.Factory
import java.time.LocalDateTime

@Factory
internal class AirQualityDtoMapper {

  fun toDomain(
    response: AirQualityResponseDto,
    coordinates: Coordinates
  ): AirQuality {

    val current = response.current
      ?: error("Air quality response missing current block")

    return AirQuality(
      coordinates = coordinates,
      europeanAqi = current.europeanAqi,
      measuredAt = LocalDateTime.parse(current.time)
    )
  }
}
