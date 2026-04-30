package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.feature.onboarding.preview.welcome.slide.PlacesSamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CARD_BASE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CARD_SLIDE_RIGHT_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CARD_STAGGER_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.CardGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.ContentHorizontal
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.ContentTopPadding
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.places.PlacesDefaults.HeaderToListGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredSlideRight
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun PlacesSlide(
  modifier: Modifier = Modifier,
  places: ImmutableList<PlaceCardUiState>,
  isSettled: Boolean = true
) {
  Box(modifier = modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .padding(top = ContentTopPadding)
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(HeaderToListGap)
    ) {
      PlacesIntro()
      PlacesList(
        places = places,
        isSettled = isSettled
      )
    }
  }
}

@Composable
private fun PlacesList(
  places: ImmutableList<PlaceCardUiState>,
  isSettled: Boolean
) {
  BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {

    val slideFromOffset = maxWidth

    Column(
      modifier = Modifier
        .padding(horizontal = ContentHorizontal)
        .align(Alignment.CenterStart),
      verticalArrangement = Arrangement.spacedBy(CardGap)
    ) {
      places.forEachIndexed { index, place ->
        PlaceRow(
          modifier = Modifier.staggeredSlideRight(
            enabled = isSettled,
            delayMs = CARD_BASE_DELAY_MS + index * CARD_STAGGER_MS,
            durationMs = CARD_SLIDE_RIGHT_DURATION_MS,
            offset = slideFromOffset
          ),
          place = place
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    PlacesSlide(places = PlacesSamples.places())
  }
}
