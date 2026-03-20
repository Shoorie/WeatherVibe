package com.weather.vibe.feature.home

import com.weather.vibe.domain.model.LocationResult
import com.weather.vibe.domain.model.WeatherData

data class HomeState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val error: String? = null,
    val isSearchActive: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<LocationResult> = emptyList(),
    val isSearching: Boolean = false
)
