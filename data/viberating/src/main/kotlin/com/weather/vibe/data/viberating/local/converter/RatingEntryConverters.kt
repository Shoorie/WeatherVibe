package com.weather.vibe.data.viberating.local.converter

import androidx.room.TypeConverter
import com.weather.vibe.domain.viberating.model.Condition
import com.weather.vibe.domain.viberating.model.PollenLevel
import java.time.LocalDate

internal class RatingEntryConverters {

  @TypeConverter
  fun fromLocalDate(value: LocalDate?): String? =
    value?.toString()

  @TypeConverter
  fun toLocalDate(value: String?): LocalDate? =
    value?.let(LocalDate::parse)

  @TypeConverter
  fun fromCondition(value: Condition?): String? =
    value?.name

  @TypeConverter
  fun toCondition(value: String?): Condition? =
    value?.let(Condition::valueOf)

  @TypeConverter
  fun fromPollenLevel(value: PollenLevel?): String? =
    value?.name

  @TypeConverter
  fun toPollenLevel(value: String?): PollenLevel? =
    value?.let(PollenLevel::valueOf)
}
