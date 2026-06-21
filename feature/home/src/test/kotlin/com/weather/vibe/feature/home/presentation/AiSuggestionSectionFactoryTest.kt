package com.weather.vibe.feature.home.presentation

import com.weather.vibe.domain.settings.model.BriefTone.WITTY_AND_FRIENDLY
import com.weather.vibe.domain.weather.usecase.BuildPlaylistQuery
import com.weather.vibe.feature.home.presentation.factory.AiSuggestionSectionFactory
import com.weather.vibe.feature.home.presentation.factory.PlaylistStateFactory
import com.weather.vibe.feature.home.presentation.fake.fakeHomeResources
import com.weather.vibe.feature.home.ui.HomeResources
import com.weather.vibe.testing.weather.fixture.WeatherSuggestionFixtures.SUGGESTION
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AiSuggestionSectionFactoryTest {

  private val resources: HomeResources = fakeHomeResources()
  private val playlistFactory = PlaylistStateFactory(buildPlaylistQuery = BuildPlaylistQuery())

  private val factory = AiSuggestionSectionFactory(
    playlistFactory = playlistFactory,
    resources = resources
  )

  @Test
  fun `when playlist built, then delegate to playlist factory`() {

    val result = factory.buildPlaylist(SUGGESTION)

    expectThat(result.mood).isEqualTo("Uplifting")
  }

  @Test
  fun `when briefing built, then text taken from suggestion`() {

    val result = factory.buildBriefing(SUGGESTION, WITTY_AND_FRIENDLY)

    expectThat(result.text).isEqualTo("Beautiful sunny day, perfect for a walk!")
  }

  @Test
  fun `when briefing built, then outfit taken from suggestion`() {

    val result = factory.buildBriefing(SUGGESTION, WITTY_AND_FRIENDLY)

    expectThat(result.outfit).isEqualTo("T-shirt, sunglasses, light cap")
  }
}
