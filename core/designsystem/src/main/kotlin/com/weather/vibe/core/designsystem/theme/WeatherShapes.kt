package com.weather.vibe.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class WeatherShapes(
  val card: Shape = RoundedCornerShape(20.dp),
  val cardSmall: Shape = RoundedCornerShape(12.dp)
)
