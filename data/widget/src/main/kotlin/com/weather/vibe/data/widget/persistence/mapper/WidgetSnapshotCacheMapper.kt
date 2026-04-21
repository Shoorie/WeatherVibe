package com.weather.vibe.data.widget.persistence.mapper

import com.weather.vibe.data.widget.persistence.WidgetSnapshotEntry
import com.weather.vibe.domain.weather.model.WeatherCondition
import com.weather.vibe.domain.weather.model.WeatherCondition.UNKNOWN
import com.weather.vibe.domain.widget.model.WidgetSnapshot
import org.koin.core.annotation.Factory

@Factory
internal class WidgetSnapshotCacheMapper(
  private val locationMapper: WidgetLocationEntryMapper
) {

  fun toDomain(entry: WidgetSnapshotEntry): WidgetSnapshot =
    WidgetSnapshot(
      condition = entry.conditionName.toWeatherCondition(),
      currentTemperature = entry.currentTemperature,
      fetchedAtEpochMillis = entry.fetchedAtEpochMillis,
      isDay = entry.isDay,
      location = locationMapper.toDomain(entry.location),
      mood = entry.mood
    )

  fun toEntry(snapshot: WidgetSnapshot): WidgetSnapshotEntry =
    WidgetSnapshotEntry.newBuilder()
      .setConditionName(snapshot.condition.name)
      .setCurrentTemperature(snapshot.currentTemperature)
      .setFetchedAtEpochMillis(snapshot.fetchedAtEpochMillis)
      .setIsDay(snapshot.isDay)
      .setLocation(locationMapper.toEntry(snapshot.location))
      .setMood(snapshot.mood)
      .build()

  private fun String.toWeatherCondition(): WeatherCondition =
    WeatherCondition.entries
      .firstOrNull { it.name == this }
      ?: UNKNOWN
}
