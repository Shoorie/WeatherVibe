package com.weather.vibe.feature.locations.presentation.fake

import com.weather.vibe.domain.weather.format.TemperatureFormatter
import io.mockk.every
import io.mockk.mockk
import kotlin.math.roundToInt

internal fun fakeTemperatureFormatter(): TemperatureFormatter = mockk {
  every { format(any(), any()) } answers {
    val celsius = firstArg<Double>()
    "${celsius.roundToInt()}°"
  }
}
