package com.weather.vibe.feature.home.presentation.state

import androidx.compose.runtime.Immutable

internal sealed interface HomeUiState {

  val allGenresRejected: Boolean get() = false
  val isPlaylistLoaded: Boolean get() = false

  fun withGenreRejecting(genre: String): HomeUiState = this
  fun withPlaylist(updated: PlaylistUiState): HomeUiState = this

  @Immutable
  data object Loading : HomeUiState

  @Immutable
  data class Loaded(
    val briefing: BriefingUiState = BriefingUiState.Loading,
    val currentWeather: CurrentWeatherUiState,
    val dailyForecast: List<DailyForecastUiState>,
    val detailsSections: DetailsSectionsUiState,
    val header: HeaderUiState,
    val hourlyForecast: List<HourlyForecastUiState>,
    val playlist: PlaylistUiState = PlaylistUiState.Loading,
    val sunriseSunset: SunriseSunsetUiState
  ) : HomeUiState {

    override val allGenresRejected: Boolean
      get() = (playlist as? PlaylistUiState.Loaded)
        ?.genres?.all { it.isRejecting }
        ?: false

    override val isPlaylistLoaded: Boolean
      get() = playlist is PlaylistUiState.Loaded

    override fun withGenreRejecting(genre: String): Loaded {

      val loadedPlaylist = playlist as? PlaylistUiState.Loaded
        ?: return this

      return copy(
        playlist = loadedPlaylist.copy(
          genres = loadedPlaylist.genres
            .map { if (it.name == genre) it.copy(isRejecting = true) else it }
        ))
    }

    override fun withPlaylist(updated: PlaylistUiState): Loaded =
      copy(playlist = updated)
  }

  @Immutable
  data class Error(val message: String) : HomeUiState
}
