package com.weather.vibe.core.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.core.ads.R
import org.koin.core.annotation.Factory

@Factory
internal class AdsResources {

  object Texts {

    @Composable
    fun previewPlaceholder(): String =
      stringResource(R.string.ads_preview_placeholder)
  }
}
