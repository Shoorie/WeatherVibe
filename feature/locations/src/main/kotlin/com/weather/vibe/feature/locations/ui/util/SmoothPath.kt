package com.weather.vibe.feature.locations.ui.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import com.weather.vibe.feature.locations.ui.LocationsDefaults.PathSmoothing

internal fun buildSmoothPath(offsets: List<Offset>): Path = Path().apply {

  if (offsets.isEmpty()) return@apply

  moveTo(offsets.first().x, offsets.first().y)

  for (index in 0 until offsets.size - 1) {

    val previous = offsets.getOrElse(index - 1) { offsets.first() }
    val current = offsets[index]
    val next = offsets[index + 1]
    val after = offsets.getOrElse(index + 2) { offsets.last() }
    val smoothing = PathSmoothing

    val controlStart = Offset(
      x = current.x + (next.x - previous.x) * smoothing,
      y = current.y + (next.y - previous.y) * smoothing
    )
    val controlEnd = Offset(
      x = next.x - (after.x - current.x) * smoothing,
      y = next.y - (after.y - current.y) * smoothing
    )

    cubicTo(
      controlStart.x, controlStart.y,
      controlEnd.x, controlEnd.y,
      next.x, next.y
    )
  }
}
