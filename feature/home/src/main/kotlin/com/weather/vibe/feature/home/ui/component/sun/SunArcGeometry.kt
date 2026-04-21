package com.weather.vibe.feature.home.ui.component.sun

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke

internal data class SunArcGeometry(
  val topLeft: Offset,
  val size: Size,
  val stroke: Stroke,
  val leftEdgeX: Float,
  val width: Float,
  val height: Float,
  val dotRadius: Float
)
