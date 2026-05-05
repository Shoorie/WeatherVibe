package com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready

import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.surface.VibeNotificationCard
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.feature.onboarding.preview.welcome.slide.ReadySamples
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.AURORA_BLOB
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeAnimationLabels.AURORA_PHASE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.WelcomeDefaults.DecelerateExpressive
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.BREATH_PHASE_OFFSET_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_BASE_DELAY_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_BELL_BASE_SCALE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_BELL_PULSE_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_BELL_PULSE_SCALE
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_BREATH_DURATION_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_BREATH_TRANSLATION_DP
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NOTIFICATIONS_STAGGER_MS
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NotificationCardGap
import com.weather.vibe.feature.onboarding.ui.screen.welcome.slide.ready.ReadyDefaults.NotificationsBellSize
import com.weather.vibe.feature.onboarding.ui.screen.welcome.staggeredFadeUp
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun ReadyNotificationsPreview(
  modifier: Modifier = Modifier,
  cards: ImmutableList<ReadyNotificationCardUiState>,
  isSettled: Boolean = true
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(NotificationCardGap)
  ) {
    cards.forEachIndexed { index, card ->
      ReadyNotificationCard(
        card = card,
        delayMs = NOTIFICATIONS_BASE_DELAY_MS + index * NOTIFICATIONS_STAGGER_MS,
        breathOffsetMs = index * BREATH_PHASE_OFFSET_MS,
        isSettled = isSettled
      )
    }
  }
}

@Composable
private fun ReadyNotificationCard(
  card: ReadyNotificationCardUiState,
  delayMs: Int,
  breathOffsetMs: Int,
  isSettled: Boolean
) {
  VibeNotificationCard(
    modifier = Modifier
      .staggeredFadeUp(delayMs = delayMs)
      .breathLoop(offsetMs = breathOffsetMs, enabled = isSettled),
    emoji = card.emoji,
    title = card.title,
    body = card.body,
    trailing = if (card.showBell) {
      { PulsingBell(enabled = isSettled) }
    } else {
      null
    }
  )
}

@Composable
private fun PulsingBell(enabled: Boolean) {
  val transition = rememberInfiniteTransition(label = AURORA_BLOB)
  val scale by transition.animateFloat(
    initialValue = NOTIFICATIONS_BELL_BASE_SCALE,
    targetValue = if (enabled) NOTIFICATIONS_BELL_PULSE_SCALE else NOTIFICATIONS_BELL_BASE_SCALE,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = NOTIFICATIONS_BELL_PULSE_DURATION_MS,
        easing = DecelerateExpressive
      ),
      repeatMode = Reverse
    ),
    label = AURORA_PHASE
  )

  Icon(
    modifier = Modifier
      .size(NotificationsBellSize)
      .graphicsLayer {
        scaleX = scale
        scaleY = scale
      },
    imageVector = Icons.Filled.Notifications,
    contentDescription = null,
    tint = colors.accent
  )
}

private fun Modifier.breathLoop(offsetMs: Int, enabled: Boolean): Modifier = composed {
  val transition = rememberInfiniteTransition(label = AURORA_BLOB)
  val phase by transition.animateFloat(
    initialValue = -1f,
    targetValue = if (enabled) 1f else -1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = NOTIFICATIONS_BREATH_DURATION_MS,
        delayMillis = offsetMs,
        easing = DecelerateExpressive
      ),
      repeatMode = Reverse
    ),
    label = AURORA_PHASE
  )
  graphicsLayer {
    translationY = phase * NOTIFICATIONS_BREATH_TRANSLATION_DP * density
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    Box {
      ReadyNotificationsPreview(cards = ReadySamples.notificationCards())
    }
  }
}
