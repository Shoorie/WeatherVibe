package com.weather.vibe.feature.home.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.weather.vibe.core.designsystem.components.GlassCard
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingExtraSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.PaddingSmall
import com.weather.vibe.core.designsystem.theme.AppDimens.SunArcHeight
import com.weather.vibe.core.designsystem.theme.AppDimens.SunArcStrokeWidth
import com.weather.vibe.core.designsystem.theme.AppDimens.SunDotRadius
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.colors
import com.weather.vibe.core.designsystem.theme.WeatherVibeTheme.typography
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.preview.SunArcSectionPreview
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunrise
import com.weather.vibe.feature.home.ui.HomeResources.Emojis.sunset
import com.weather.vibe.feature.home.ui.HomeResources.Texts.dayLengthLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunriseLabel
import com.weather.vibe.feature.home.ui.HomeResources.Texts.sunsetLabel
import androidx.compose.runtime.remember
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SunArcSection(
  modifier: Modifier = Modifier,
  state: SunriseSunsetUiState
) {
  GlassCard(modifier = modifier.fillMaxWidth()) {
    SunArcCanvas(sunProgress = state.sunProgress)
    Spacer(modifier = Modifier.height(PaddingSmall))
    SunTimesRow(state = state)
    if (state.dayLength.isNotEmpty()) {
      Spacer(modifier = Modifier.height(PaddingExtraSmall))
      Text(
        text = "${dayLengthLabel()}: ${state.dayLength}",
        style = typography.bodySmall,
        color = colors.textTertiary,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
private fun SunArcCanvas(
  modifier: Modifier = Modifier,
  sunProgress: Float
) {
  val trackColor = colors.outline
  val accentColor = colors.accent
  val accentDarkColor = colors.accentDark
  val glowColor = colors.accent.copy(alpha = GLOW_ALPHA)
  val gradientBrush = remember(accentColor, accentDarkColor) {
    Brush.linearGradient(colors = listOf(accentColor, accentDarkColor))
  }

  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(SunArcHeight)
  ) {
    val padding = ARC_PADDING_RATIO * size.width
    val strokeWidth = SunArcStrokeWidth.toPx()
    val dotRadius = SunDotRadius.toPx()
    val arcLeft = padding
    val arcWidth = size.width - padding * 2
    val arcHeight = size.height - dotRadius * 2
    val arcTopLeft = Offset(arcLeft, dotRadius)
    val arcSize = Size(arcWidth, arcHeight * 2)
    val arcStroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

    drawArc(
      color = trackColor,
      startAngle = 180f,
      sweepAngle = 180f,
      useCenter = false,
      topLeft = arcTopLeft,
      size = arcSize,
      style = arcStroke
    )

    if (sunProgress > 0f) {
      drawArc(
        brush = gradientBrush,
        startAngle = 180f,
        sweepAngle = ARC_SWEEP * sunProgress,
        useCenter = false,
        topLeft = arcTopLeft,
        size = arcSize,
        style = arcStroke
      )
    }

    drawSunDot(
      sunProgress = sunProgress,
      accentColor = accentColor,
      glowColor = glowColor,
      arcLeft = arcLeft,
      arcWidth = arcWidth,
      arcHeight = arcHeight,
      dotRadius = dotRadius
    )
  }
}

private fun DrawScope.drawSunDot(
  sunProgress: Float,
  accentColor: Color,
  glowColor: Color,
  arcLeft: Float,
  arcWidth: Float,
  arcHeight: Float,
  dotRadius: Float
) {
  val angle = PI + PI * sunProgress
  val centerX = arcLeft + arcWidth / 2
  val centerY = dotRadius + arcHeight
  val dotCenter = Offset(
    x = centerX + (arcWidth / 2) * cos(angle).toFloat(),
    y = centerY + arcHeight * sin(angle).toFloat()
  )

  drawCircle(
    color = glowColor,
    radius = dotRadius * GLOW_MULTIPLIER,
    center = dotCenter
  )
  drawCircle(
    color = accentColor,
    radius = dotRadius,
    center = dotCenter
  )
}

@Composable
private fun SunTimesRow(
  modifier: Modifier = Modifier,
  state: SunriseSunsetUiState
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column(horizontalAlignment = Alignment.Start) {
      Text(
        text = "${sunrise()} ${state.sunriseTime}",
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Text(
        text = sunriseLabel(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
    Column(horizontalAlignment = Alignment.End) {
      Text(
        text = "${state.sunsetTime} ${sunset()}",
        style = typography.titleMedium,
        color = colors.onBackground
      )
      Text(
        text = sunsetLabel(),
        style = typography.labelSmall,
        color = colors.onSurfaceVariant
      )
    }
  }
}

private const val ARC_PADDING_RATIO = 0.08f
private const val ARC_SWEEP = 180f
private const val GLOW_ALPHA = 0.3f
private const val GLOW_MULTIPLIER = 2.5f

@PreviewLightDark
@Composable
private fun Preview(
  @PreviewParameter(SunArcSectionPreview::class)
  state: SunriseSunsetUiState
) {
  WeatherVibeTheme {
    SunArcSection(state = state)
  }
}
