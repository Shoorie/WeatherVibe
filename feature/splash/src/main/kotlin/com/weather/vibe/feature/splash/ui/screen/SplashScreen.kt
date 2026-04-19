package com.weather.vibe.feature.splash.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.domain.location.model.Location
import com.weather.vibe.feature.splash.presentation.SplashEvent
import com.weather.vibe.feature.splash.presentation.SplashEvent.NavigateToHome
import com.weather.vibe.feature.splash.presentation.SplashEvent.NavigateToOnboarding
import com.weather.vibe.feature.splash.presentation.SplashViewModel
import com.weather.vibe.feature.splash.ui.SplashResources.Texts.appName
import com.weather.vibe.feature.splash.ui.SplashResources.Texts.appTagline
import com.weather.vibe.feature.splash.ui.SplashTextStyles.mutedOnBrand
import com.weather.vibe.feature.splash.ui.SplashTextStyles.onBrand
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.ExitDuration
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.HoldBeforeExit
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.IconBottomGap
import com.weather.vibe.feature.splash.ui.screen.SplashDefaults.WordmarkBottomGap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
  modifier: Modifier = Modifier,
  onNavigateToHome: (Location) -> Unit,
  onNavigateToOnboarding: () -> Unit
) {

  val viewModel: SplashViewModel = koinViewModel()
  val anim = remember { SplashAnimatables() }
  var animationFinished by remember { mutableStateOf(false) }

  LaunchedEffect(Unit) {
    launch { taglineFadesIn(anim = anim) }
    wordmarkFadesIn(anim = anim)
    delay(HoldBeforeExit)
    sceneFadesOut(anim = anim)
    delay(ExitDuration.toLong())
    animationFinished = true
  }

  LaunchedEffect(viewModel, animationFinished) {
    if (!animationFinished) return@LaunchedEffect
    dispatchEvent(
      event = viewModel.event.first(),
      onNavigateToHome = onNavigateToHome,
      onNavigateToOnboarding = onNavigateToOnboarding
    )
  }

  val brandColor = onBrand()
  val mutedBrandColor = mutedOnBrand()
  val wordmarkText = appName()
  val annotatedWordmark = remember(wordmarkText, brandColor, mutedBrandColor) {
    buildWordmark(
      wordmark = wordmarkText,
      mutedColor = mutedBrandColor,
      accentColor = brandColor
    )
  }

  SplashContent(
    modifier = modifier,
    anim = anim,
    annotatedWordmark = annotatedWordmark,
    brandColor = brandColor,
    mutedBrandColor = mutedBrandColor
  )
}

@Composable
private fun SplashContent(
  modifier: Modifier = Modifier,
  anim: SplashAnimatables,
  annotatedWordmark: AnnotatedString,
  brandColor: Color,
  mutedBrandColor: Color
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(color = colors.accent)
      .graphicsLayer { alpha = anim.exitAlpha.value },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    SplashEqualizer(barColor = brandColor)
    Spacer(modifier = Modifier.height(IconBottomGap))
    SplashWordmark(
      wordmark = annotatedWordmark,
      alpha = anim.wordmarkAlpha.value,
      slideDp = anim.wordmarkSlide.value
    )
    Spacer(modifier = Modifier.height(WordmarkBottomGap))
    SplashTagline(
      alpha = anim.taglineAlpha.value,
      color = mutedBrandColor
    )
  }
}

private fun dispatchEvent(
  event: SplashEvent,
  onNavigateToHome: (Location) -> Unit,
  onNavigateToOnboarding: () -> Unit
) {
  when (event) {
    is NavigateToHome -> onNavigateToHome(event.location)
    NavigateToOnboarding -> onNavigateToOnboarding()
  }
}

private fun buildWordmark(
  wordmark: String,
  mutedColor: Color,
  accentColor: Color
): AnnotatedString {

  val accentStart = wordmark
    .indexOf(ACCENT_SPLIT_PREFIX)
    .takeIf { it >= 0 }
    ?: wordmark.length

  return buildAnnotatedString {
    withStyle(SpanStyle(color = mutedColor)) {
      append(wordmark.substring(startIndex = 0, endIndex = accentStart))
    }
    withStyle(SpanStyle(color = accentColor)) {
      append(wordmark.substring(startIndex = accentStart))
    }
  }
}

@Composable
private fun SplashWordmark(
  modifier: Modifier = Modifier,
  wordmark: AnnotatedString,
  alpha: Float,
  slideDp: Float
) {
  Text(
    modifier = modifier.graphicsLayer {
      this.alpha = alpha
      translationY = slideDp * density
    },
    text = wordmark,
    style = typography.displaySmall,
    fontWeight = FontWeight.SemiBold
  )
}

@Composable
private fun SplashTagline(
  modifier: Modifier = Modifier,
  alpha: Float,
  color: Color
) {
  Text(
    modifier = modifier.graphicsLayer { this.alpha = alpha },
    text = appTagline(),
    style = typography.bodyMedium,
    color = color
  )
}

private const val ACCENT_SPLIT_PREFIX = "Vibe"

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    SplashContent(
      anim = SplashAnimatables(),
      annotatedWordmark = AnnotatedString("WeatherVibe"),
      brandColor = Color.White,
      mutedBrandColor = Color.White.copy(alpha = 0.7f)
    )
  }
}
