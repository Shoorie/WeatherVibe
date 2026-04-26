package com.weather.vibe.data.viberating.local.mapper

import com.weather.vibe.data.viberating.local.entity.WeatherSnapshotEmbedded
import com.weather.vibe.domain.viberating.model.WeatherSnapshot
import org.koin.core.annotation.Factory

@Factory
internal class WeatherSnapshotMapper {

  fun toDomain(embedded: WeatherSnapshotEmbedded): WeatherSnapshot =
    WeatherSnapshot(
      temperatureC = embedded.temperatureC,
      feelsLikeC = embedded.feelsLikeC,
      condition = embedded.condition,
      humidityPercent = embedded.humidityPercent,
      windKph = embedded.windKph,
      pressureHpa = embedded.pressureHpa,
      airQualityIndex = embedded.airQualityIndex,
      pollenLevel = embedded.pollenLevel
    )

  fun toEmbedded(snapshot: WeatherSnapshot): WeatherSnapshotEmbedded =
    WeatherSnapshotEmbedded(
      temperatureC = snapshot.temperatureC,
      feelsLikeC = snapshot.feelsLikeC,
      condition = snapshot.condition,
      humidityPercent = snapshot.humidityPercent,
      windKph = snapshot.windKph,
      pressureHpa = snapshot.pressureHpa,
      airQualityIndex = snapshot.airQualityIndex,
      pollenLevel = snapshot.pollenLevel
    )
}
