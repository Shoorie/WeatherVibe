package com.weather.vibe.feature.locations.presentation.fake

import com.weather.vibe.feature.locations.ui.LocationsResources
import io.mockk.every
import io.mockk.mockk

internal const val DEFAULT_ERROR = "Something went wrong."
internal const val COMPARE_ERROR = "Couldn't load comparison."

internal fun fakeLocationsResources(): LocationsResources =
  mockk<LocationsResources>(relaxed = false).apply {
    every { defaultError() } returns DEFAULT_ERROR
    every { compareError() } returns COMPARE_ERROR
  }
