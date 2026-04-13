package com.weather.vibe.feature.search.ui.component.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.theme.AppDimens.IconSize
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Medium
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding.Small
import com.weather.vibe.core.designsystem.theme.AppDimens.Stroke
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.search.preview.SearchBarPreview
import com.weather.vibe.feature.search.ui.SearchDefaults.SearchFieldMinHeight
import com.weather.vibe.feature.search.ui.SearchResources.Texts.clearContentDescription
import com.weather.vibe.feature.search.ui.SearchResources.Texts.searchHint

@Composable
internal fun SearchField(
  modifier: Modifier = Modifier,
  query: String,
  onQueryChange: (String) -> Unit
) {

  val focusRequester = remember { FocusRequester() }

  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = SearchFieldMinHeight)
      .clip(shapes.pill)
      .background(colors.surfaceVariant)
      .border(Stroke.Border, colors.outlineVariant, shapes.pill)
      .padding(horizontal = Medium),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Small)
  ) {
    Icon(
      modifier = Modifier.size(IconSize.Small),
      imageVector = Icons.Default.Search,
      contentDescription = null,
      tint = colors.onSurfaceVariant
    )
    SearchFieldInput(
      modifier = Modifier
        .weight(1f)
        .focusRequester(focusRequester),
      query = query,
      onQueryChange = onQueryChange
    )
    if (query.isNotEmpty()) {
      ClearIcon(onClick = { onQueryChange("") })
    }
  }
}

@Composable
private fun SearchFieldInput(
  modifier: Modifier = Modifier,
  query: String,
  onQueryChange: (String) -> Unit
) {
  BasicTextField(
    value = query,
    onValueChange = onQueryChange,
    modifier = modifier,
    textStyle = typography.bodyLarge.copy(color = colors.onBackground),
    cursorBrush = SolidColor(colors.accent),
    singleLine = true,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onSearch = {}),
    decorationBox = { innerTextField ->
      Box {
        if (query.isEmpty()) {
          Text(
            text = searchHint(),
            style = typography.bodyLarge,
            color = colors.onSurfaceVariant
          )
        }
        innerTextField()
      }
    }
  )
}

@Composable
private fun ClearIcon(onClick: () -> Unit) {

  val label = clearContentDescription()

  Icon(
    modifier = Modifier
      .size(IconSize.Small)
      .clip(shapes.pill)
      .clickable(
        role = Role.Button,
        onClickLabel = label,
        onClick = onClick
      ),
    imageVector = Icons.Default.Close,
    contentDescription = label,
    tint = colors.onSurfaceVariant
  )
}

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SearchBarPreview::class)
  query: String
) {
  WeatherVibeTheme {
    SearchField(
      query = query,
      onQueryChange = {}
    )
  }
}
