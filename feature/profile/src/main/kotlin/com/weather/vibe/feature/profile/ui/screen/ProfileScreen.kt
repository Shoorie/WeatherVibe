package com.weather.vibe.feature.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraLarge
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.rememberAppBackgroundBrush
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenAbout
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenLocations
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenNotifications
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPersonalization
import com.weather.vibe.feature.profile.presentation.ProfileEvent.OpenPrivacy
import com.weather.vibe.feature.profile.presentation.ProfileViewModel
import com.weather.vibe.feature.profile.presentation.state.ProfileStatType
import com.weather.vibe.feature.profile.presentation.state.ProfileStatUiState
import com.weather.vibe.feature.profile.presentation.state.ProfileUiState
import com.weather.vibe.feature.profile.preview.ProfilePreview
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_HERO
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_MOOD
import com.weather.vibe.feature.profile.ui.ProfileKeys.KEY_QUICK_STATS
import com.weather.vibe.feature.profile.ui.component.editsheet.EditProfileSheet
import com.weather.vibe.feature.profile.ui.component.header.ProfileHero
import com.weather.vibe.feature.profile.ui.component.mood.MoodTeaserCard
import com.weather.vibe.feature.profile.ui.component.stats.ProfileStatCard
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  onOpenPersonalization: () -> Unit,
  onOpenNotifications: () -> Unit,
  onOpenPrivacy: () -> Unit,
  onOpenAbout: () -> Unit,
  onOpenLocations: () -> Unit,
  onOpenVibeHistory: () -> Unit
) {

  val viewModel: ProfileViewModel = koinViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()
  val callbacks = rememberProfileCallbacks(dispatch = viewModel::dispatch)

  LaunchedEffect(Unit) {
    viewModel.event.collect { event ->
      when (event) {
        OpenPersonalization -> onOpenPersonalization()
        OpenNotifications -> onOpenNotifications()
        OpenPrivacy -> onOpenPrivacy()
        OpenAbout -> onOpenAbout()
        OpenLocations -> onOpenLocations()
      }
    }
  }

  ProfileContent(
    state = state,
    callbacks = callbacks,
    onOpenVibeHistory = onOpenVibeHistory
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileContent(
  modifier: Modifier = Modifier,
  state: ProfileUiState,
  callbacks: ProfileCallbacks,
  onOpenVibeHistory: () -> Unit = {}
) {

  val contentPadding = remember {
    PaddingValues(
      start = Medium,
      end = Medium,
      top = Medium,
      bottom = ExtraLarge
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(rememberAppBackgroundBrush())
      .statusBarsPadding(),
    contentPadding = contentPadding,
    verticalArrangement = Arrangement.spacedBy(Medium)
  ) {
    item(key = KEY_HERO) {
      ProfileHero(
        header = state.header,
        onEditClick = callbacks.onEditUsernameClick,
        onBriefToneClick = callbacks.onPersonalizationClick
      )
    }
    item(key = KEY_QUICK_STATS) {
      QuickStatsRow(
        stats = state.quickStats,
        onStatClick = callbacks.onStatClick
      )
    }
    item(key = KEY_MOOD) {
      MoodTeaserCard(onClick = onOpenVibeHistory)
    }
    navigationItems(callbacks = callbacks)
  }

  if (state.editSheet.isVisible) {
    EditProfileSheet(
      state = state.editSheet,
      onDismiss = callbacks.onEditUsernameDismiss,
      onUsernameChange = callbacks.onUsernameChange,
      onSubmit = callbacks.onEditUsernameSubmit
    )
  }
}

@Composable
private fun QuickStatsRow(
  stats: ImmutableList<ProfileStatUiState>,
  onStatClick: (ProfileStatType) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    stats.forEach { stat ->
      ProfileStatCard(
        modifier = Modifier.weight(1f),
        stat = stat,
        onClick = { onStatClick(stat.type) }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(ProfilePreview::class)
  state: ProfileUiState
) {
  WeatherVibeTheme {
    ProfileContent(
      state = state,
      callbacks = ProfileCallbacks.Noop
    )
  }
}
