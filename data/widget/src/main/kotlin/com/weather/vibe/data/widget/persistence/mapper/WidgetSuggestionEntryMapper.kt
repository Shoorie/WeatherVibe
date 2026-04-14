package com.weather.vibe.data.widget.persistence.mapper

import com.weather.vibe.data.widget.persistence.WidgetSuggestionEntry
import com.weather.vibe.domain.weather.model.WeatherSuggestion
import org.koin.core.annotation.Factory

@Factory
internal class WidgetSuggestionEntryMapper {

  fun toDomain(entry: WidgetSuggestionEntry): WeatherSuggestion =
    WeatherSuggestion(
      briefText = entry.briefText,
      genres = entry.genresList.toList(),
      mood = entry.mood,
      moodDescription = entry.moodDescription
    )

  fun toEntry(suggestion: WeatherSuggestion): WidgetSuggestionEntry =
    WidgetSuggestionEntry.newBuilder()
      .setBriefText(suggestion.briefText)
      .addAllGenres(suggestion.genres)
      .setMood(suggestion.mood)
      .setMoodDescription(suggestion.moodDescription)
      .build()
}
