package com.weather.vibe.data.ads.initializer

import com.weather.vibe.data.ads.BuildConfig
import org.koin.core.annotation.Single

@Single
class TestDeviceIdsProvider {

  fun deviceIds(): List<String> =
    BuildConfig.ADMOB_TEST_DEVICE_IDS
      .split(SEPARATOR)
      .map { id -> id.trim() }
      .filter { id -> id.isNotEmpty() }

  private companion object {
    const val SEPARATOR = ","
  }
}
