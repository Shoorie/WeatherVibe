package com.weather.vibe.feature.locations.ui.component.compare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Large
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.locations.presentation.state.LocationComparePairUiState
import com.weather.vibe.feature.locations.preview.LocationsPreviewData.comparePair
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareSubtitle
import com.weather.vibe.feature.locations.ui.LocationsResources.Texts.compareTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocationCompareSheet(
  modifier: Modifier = Modifier,
  pair: LocationComparePairUiState,
  onDismiss: () -> Unit,
  sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
  ModalBottomSheet(
    modifier = modifier,
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = colors.sheetSurface
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = Medium)
        .padding(bottom = Large),
      verticalArrangement = Arrangement.spacedBy(Large)
    ) {
      CompareSheetHeader(
        firstName = pair.first.card.name,
        secondName = pair.second.card.name
      )
      LocationComparePanel(pair = pair)
    }
  }
}

@Composable
private fun CompareSheetHeader(
  firstName: String,
  secondName: String
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(
        horizontal = Small,
        vertical = ExtraSmall
      ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ExtraSmall)
  ) {
    Text(
      text = compareSubtitle(),
      style = typography.labelSmall,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
    Text(
      text = compareTitle(first = firstName, second = secondName),
      style = typography.titleLarge,
      color = colors.onBackground,
      textAlign = TextAlign.Center,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    LocationCompareSheet(
      pair = comparePair,
      onDismiss = {}
    )
  }
}
