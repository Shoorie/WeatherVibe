package com.weather.vibe.feature.home.presentation.state

import com.weather.vibe.feature.home.presentation.fixture.MetricFixtures.METRICS_SECTIONS
import com.weather.vibe.feature.home.preview.HomePreviewData.forecastSection
import com.weather.vibe.feature.home.preview.HomePreviewData.loadedPlaylist
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class HomeUiStateUpdatesTest {

  @Test
  fun `given loaded state, when suggestion applied, then update briefing`() {

    val briefing = BriefingUiState.Loaded("Sunny day!")

    val result = loadedState().withSuggestion(
      briefing = briefing,
      playlist = PlaylistUiState.Loading
    )

    expectThat(result).isA<HomeUiState.Loaded>()
      .get { aiSuggestion.briefing }.isEqualTo(briefing)
  }

  @Test
  fun `given loaded state, when suggestion applied, then update playlist`() {

    val result = loadedState().withSuggestion(
      briefing = BriefingUiState.Loading,
      playlist = loadedPlaylist
    )

    expectThat(result).isA<HomeUiState.Loaded>()
      .get { aiSuggestion.playlist }.isEqualTo(loadedPlaylist)
  }

  @Test
  fun `given loading state, when suggestion applied, then return unchanged`() {

    val result = HomeUiState.Loading.withSuggestion(
      briefing = BriefingUiState.Loaded("text"),
      playlist = PlaylistUiState.Loading
    )

    expectThat(result).isA<HomeUiState.Loading>()
  }

  @Test
  fun `given error state, when suggestion applied, then return unchanged`() {

    val result = HomeUiState.Error("Something went wrong").withSuggestion(
      briefing = BriefingUiState.Loaded("text"),
      playlist = PlaylistUiState.Loading
    )

    expectThat(result).isA<HomeUiState.Error>()
  }

  private fun loadedState(): HomeUiState.Loaded =
    HomeUiState.Loaded(details = METRICS_SECTIONS, forecast = forecastSection)
}
