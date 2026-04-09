package com.weather.vibe.feature.home.presentation.fixture

import com.weather.vibe.domain.weather.model.WeatherSuggestion

internal object WeatherSuggestionFixtures {

  const val BRIEF_TEXT = "Beautiful sunny day, perfect for a walk!"
  const val MOOD = "Uplifting"
  const val MOOD_DESCRIPTION = "Bright and energetic vibes"

  const val GENRE_INDIE = "Indie Pop"
  const val GENRE_ELECTRONIC = "Electronic"
  const val GENRE_JAZZ = "Jazz"

  val DEFAULT_GENRES = listOf(GENRE_INDIE, GENRE_ELECTRONIC, GENRE_JAZZ)

  val SUGGESTION = suggestion()

  val SINGLE_GENRE = suggestion(genres = listOf(GENRE_INDIE))

  val WHITESPACE_GENRES = suggestion(
    genres = listOf(
      "  Indie Pop  ",
      " Electronic ",
      "  Jazz  "
    )
  )

  fun suggestion(
    briefText: String = BRIEF_TEXT,
    genres: List<String> = DEFAULT_GENRES,
    mood: String = MOOD,
    moodDescription: String = MOOD_DESCRIPTION
  ): WeatherSuggestion = WeatherSuggestion(
    briefText = briefText,
    genres = genres,
    mood = mood,
    moodDescription = moodDescription
  )
}
