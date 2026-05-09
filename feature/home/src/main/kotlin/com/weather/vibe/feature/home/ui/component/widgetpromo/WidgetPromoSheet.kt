package com.weather.vibe.feature.home.ui.component.widgetpromo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weather.vibe.core.designsystem.components.button.VibeButtonStack
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.ExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.ui.HomeWidgetPromoTexts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WidgetPromoSheet(
  modifier: Modifier = Modifier,
  onAddClick: () -> Unit,
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
        .padding(horizontal = Medium)
        .padding(bottom = Medium),
      verticalArrangement = Arrangement.spacedBy(Medium),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      WidgetPromoMiniPreview()
      WidgetPromoHeader()
      VibeButtonStack(
        primaryLabel = HomeWidgetPromoTexts.primaryAction(),
        onPrimaryClick = onAddClick,
        secondaryLabel = HomeWidgetPromoTexts.secondaryAction(),
        onSecondaryClick = onDismiss
      )
    }
  }
}

@Composable
private fun WidgetPromoHeader() {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(ExtraSmall),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      modifier = Modifier.semantics { heading() },
      text = HomeWidgetPromoTexts.title(),
      style = typography.titleLarge,
      color = colors.onSurface,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center
    )
    Text(
      modifier = Modifier.padding(horizontal = Small),
      text = HomeWidgetPromoTexts.subtitle(),
      style = typography.bodyMedium,
      color = colors.onSurfaceVariant,
      textAlign = TextAlign.Center
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun Preview() {
  WeatherVibeTheme {
    WidgetPromoSheet(
      onAddClick = {},
      onDismiss = {}
    )
  }
}
