package com.weather.vibe.feature.viberating.ui.rating

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.weather.vibe.core.designsystem.components.mood.MoodFace
import com.weather.vibe.core.designsystem.components.mood.MoodFaceDefaults
import com.weather.vibe.core.designsystem.theme.AppDimens.Padding
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.shapes
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.core.designsystem.theme.ratingColor
import com.weather.vibe.feature.viberating.ui.VibeRatingResources
import com.weather.vibe.feature.viberating.ui.VibeRatingResources.Texts
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.ButtonIconSize
import com.weather.vibe.feature.viberating.ui.rating.RatingCardDefaults.TouchTarget

@Composable
internal fun RatedContent(
  rating: Int,
  onEditClicked: () -> Unit,
  onViewHistoryClicked: () -> Unit
) {
  val ratingLabel = VibeRatingResources.scaleLabel(rating)
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {},
    verticalAlignment = Alignment.CenterVertically
  ) {
    MoodFace(
      rating = rating,
      active = true,
      size = MoodFaceDefaults.SizeLarge
    )
    Spacer(Modifier.width(Padding.Medium))
    Text(
      text = Texts.ratedLabel(label = ratingLabel, rating = rating),
      style = typography.titleMedium,
      color = ratingColor(rating),
      fontWeight = FontWeight.Bold,
      modifier = Modifier.weight(1f)
    )
    TextButton(
      onClick = onEditClicked,
      modifier = Modifier.defaultMinSize(minWidth = TouchTarget, minHeight = TouchTarget)
    ) {
      Text(
        text = Texts.change(),
        style = typography.labelMedium,
        color = colors.onSurfaceVariant
      )
    }
  }
  Spacer(Modifier.height(Padding.Medium))
  Button(
    onClick = onViewHistoryClicked,
    modifier = Modifier.fillMaxWidth(),
    shape = shapes.cardSmall,
    colors = ButtonDefaults.buttonColors(
      containerColor = colors.accent,
      contentColor = colors.onAccent
    )
  ) {
    Icon(
      imageVector = Icons.Filled.DateRange,
      contentDescription = null,
      modifier = Modifier.size(ButtonIconSize)
    )
    Spacer(Modifier.width(Padding.Small))
    Text(Texts.viewHistory())
  }
}
