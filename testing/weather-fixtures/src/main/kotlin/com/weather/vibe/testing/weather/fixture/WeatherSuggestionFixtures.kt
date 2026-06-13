package com.weather.vibe.testing.weather.fixture

import com.weather.vibe.domain.settings.model.BriefTone
import com.weather.vibe.domain.settings.model.BriefTone.FORMAL
import com.weather.vibe.domain.weather.model.CachedWeatherSuggestion
import com.weather.vibe.domain.weather.model.SimplifiedCondition.SUNNY
import com.weather.vibe.domain.weather.model.TemperatureRange.WARM
import com.weather.vibe.domain.weather.model.TimeOfDay.AFTERNOON
import com.weather.vibe.domain.weather.model.WeatherKey
import com.weather.vibe.domain.weather.model.WeatherSuggestion

object WeatherSuggestionFixtures {

  const val BRIEF_TEXT = "Beautiful sunny day, perfect for a walk!"
  const val MOOD = "Uplifting"
  const val MOOD_DESCRIPTION = "Bright and energetic vibes"
  const val OUTFIT_SUGGESTION = "T-shirt, sunglasses, light cap"

  const val GENRE_INDIE = "Indie Pop"
  const val GENRE_ELECTRONIC = "Electronic"
  const val GENRE_JAZZ = "Jazz"

  const val FETCHED_AT = 1_700_000_000_000L
  const val LANGUAGE_TAG = "en"
  const val LOCATION_ID = "52.0,21.0"

  val DEFAULT_GENRES = listOf(GENRE_INDIE, GENRE_ELECTRONIC, GENRE_JAZZ)
  val DEFAULT_WEATHER_KEY: WeatherKey =
    WeatherKey(condition = SUNNY, temperature = WARM, timeOfDay = AFTERNOON)

  val SUGGESTION = suggestion()

  val SINGLE_GENRE = suggestion(genres = listOf(GENRE_INDIE))

  val WHITESPACE_GENRES = suggestion(
    genres = listOf(
      "  Indie Pop  ",
      " Electronic ",
      "  Jazz  "
    )
  )

  val SUGGESTION_JSON_WITH_OUTFIT = """
    {
      "briefText": "$BRIEF_TEXT",
      "mood": "$MOOD",
      "moodDescription": "$MOOD_DESCRIPTION",
      "outfitSuggestion": "$OUTFIT_SUGGESTION",
      "genres": ["$GENRE_INDIE", "$GENRE_ELECTRONIC", "$GENRE_JAZZ"]
    }
  """.trimIndent()

  val SUGGESTION_JSON_WITH_BLANK_OUTFIT = SUGGESTION_JSON_WITH_OUTFIT
    .replace("\"$OUTFIT_SUGGESTION\"", "\"\"")

  val SUGGESTION_JSON_WITH_WHITESPACE_OUTFIT = SUGGESTION_JSON_WITH_OUTFIT
    .replace("\"$OUTFIT_SUGGESTION\"", "\"   $OUTFIT_SUGGESTION   \"")

  fun suggestion(
    briefText: String = BRIEF_TEXT,
    genres: List<String> = DEFAULT_GENRES,
    mood: String = MOOD,
    moodDescription: String = MOOD_DESCRIPTION,
    outfitSuggestion: String? = OUTFIT_SUGGESTION
  ): WeatherSuggestion = WeatherSuggestion(
    briefText = briefText,
    genres = genres,
    mood = mood,
    moodDescription = moodDescription,
    outfitSuggestion = outfitSuggestion
  )

  fun cachedSuggestion(
    fetchedAt: Long = FETCHED_AT,
    suggestion: WeatherSuggestion = SUGGESTION,
    tone: BriefTone = FORMAL,
    weatherKey: WeatherKey = DEFAULT_WEATHER_KEY
  ): CachedWeatherSuggestion = CachedWeatherSuggestion(
    fetchedAt = fetchedAt,
    suggestion = suggestion,
    tone = tone,
    weatherKey = weatherKey
  )
}
