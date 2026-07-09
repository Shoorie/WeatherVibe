package com.weather.vibe.feature.home.ui.component.briefing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.ads.rewarded.rememberRewardedAdController
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.persona.PersonaColorKey
import com.weather.vibe.core.designsystem.theme.persona.PersonaPalette
import com.weather.vibe.domain.ads.placement.AdPlacement.BriefRefreshRewarded
import com.weather.vibe.feature.home.presentation.state.BriefingPersonaUiState
import com.weather.vibe.feature.home.presentation.state.BriefingUiState.Limit
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingLimitPremium
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingLimitReset
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingLimitTitle
import com.weather.vibe.feature.home.ui.HomeAiSuggestionTexts.aiBriefingLimitWatch
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.DisabledAlpha
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.LimitBlurRadius
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.LimitOverlayMinHeight
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.WatchSpinner
import com.weather.vibe.feature.home.ui.component.briefing.BriefingDefaults.WatchSpinnerStroke

@Composable
internal fun BriefingLimitContent(
  modifier: Modifier = Modifier,
  onBuyPremium: () -> Unit,
  onWatchAdEarned: () -> Unit,
  state: Limit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .heightIn(min = LimitOverlayMinHeight)
  ) {
    Text(
      text = state.teaser,
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      modifier = Modifier
        .fillMaxWidth()
        .blur(LimitBlurRadius)
    )
    Text(
      text = state.teaser,
      style = typography.bodyMedium,
      color = colors.onPrimaryContainer,
      maxLines = 1,
      overflow = TextOverflow.Clip,
      modifier = Modifier.fillMaxWidth()
    )
    LimitOverlay(
      modifier = Modifier.align(Alignment.BottomCenter),
      onBuyPremium = onBuyPremium,
      onWatchAdEarned = onWatchAdEarned
    )
  }
}

@Composable
private fun LimitOverlay(
  modifier: Modifier = Modifier,
  onBuyPremium: () -> Unit,
  onWatchAdEarned: () -> Unit
) {
  val controller = rememberRewardedAdController()
  val scope = rememberCoroutineScope()
  val frosted = Brush.verticalGradient(
    colors = listOf(
      Color.Transparent,
      colors.primaryContainer,
      colors.primaryContainer
    )
  )
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(frosted)
      .padding(top = Small),
    verticalArrangement = Arrangement.spacedBy(Small)
  ) {
    Text(
      text = aiBriefingLimitTitle(),
      style = typography.titleSmall,
      color = colors.onSurface
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(Small)
    ) {
      LimitButton(
        background = PersonaPalette.premiumBrush(),
        enabled = !controller.isWatching,
        label = aiBriefingLimitPremium(),
        onClick = onBuyPremium
      )
      WatchVideoButton(
        isLoading = controller.isWatching,
        onWatch = { controller.rewardOnWatch(scope, BriefRefreshRewarded, onWatchAdEarned) }
      )
    }
    Text(
      text = aiBriefingLimitReset(),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
private fun RowScope.LimitButton(
  background: Brush,
  enabled: Boolean,
  label: String,
  onClick: () -> Unit
) {
  Text(
    text = label,
    style = typography.titleSmall,
    color = colors.onAccent,
    textAlign = TextAlign.Center,
    modifier = Modifier
      .weight(1f)
      .clip(shapes.card)
      .background(background)
      .alpha(if (enabled) 1f else DisabledAlpha)
      .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
      .padding(vertical = Small)
  )
}

@Composable
private fun RowScope.WatchVideoButton(
  isLoading: Boolean,
  onWatch: () -> Unit
) {
  Box(
    modifier = Modifier
      .weight(1f)
      .clip(shapes.card)
      .background(colors.rowSurface)
      .clickable(enabled = !isLoading, role = Role.Button, onClick = onWatch)
      .padding(vertical = Small),
    contentAlignment = Alignment.Center
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        modifier = Modifier.size(WatchSpinner),
        color = colors.accent,
        strokeWidth = WatchSpinnerStroke
      )
    } else {
      Text(
        text = aiBriefingLimitWatch(),
        style = typography.titleSmall,
        color = colors.accent,
        textAlign = TextAlign.Center
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    BriefingLimitContent(
      modifier = Modifier.background(colors.primaryContainer).padding(Small),
      onBuyPremium = {},
      onWatchAdEarned = {},
      state = Limit(
        persona = BriefingPersonaUiState(colorKey = PersonaColorKey.COACH),
        teaser = "A mild, partly cloudy day. Light breeze in the afternoon, " +
          "perfect for a walk before the evening rain rolls in."
      )
    )
  }
}
