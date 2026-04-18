package com.weather.vibe.feature.home.presentation

import com.weather.vibe.feature.home.presentation.state.SharePosterUiState

internal sealed interface HomeEvent {

  data class SharePoster(val state: SharePosterUiState) : HomeEvent
}
