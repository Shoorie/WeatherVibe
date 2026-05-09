package com.weather.vibe.core.ads.data

import com.weather.vibe.core.ads.BuildConfig
import org.koin.core.annotation.Single

@Single
class TestDeviceIdsProvider {

  fun deviceIds(): List<String> =
    BuildConfig.ADMOB_TEST_DEVICE_IDS
      .split(SEPARATOR)
      .map { it.trim() }
      .filter { it.isNotEmpty() }

  private companion object {
    const val SEPARATOR = ","
  }
}
