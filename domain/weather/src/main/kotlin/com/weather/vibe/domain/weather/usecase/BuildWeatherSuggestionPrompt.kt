package com.weather.vibe.domain.weather.usecase

import com.weather.vibe.domain.weather.model.WeatherSuggestionPromptInput

interface BuildWeatherSuggestionPrompt {

  operator fun invoke(input: WeatherSuggestionPromptInput): String
}
