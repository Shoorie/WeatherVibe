package com.weather.vibe.feature.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.search.R
import org.koin.core.annotation.Factory

@Factory
internal class SearchResources {

  object Emojis {
    fun clock(): String = "\uD83D\uDD58"
    fun locationPin(): String = "\uD83D\uDCCD"
  }

  object Texts {

    @Composable
    fun noResultsFound(query: String): String =
      stringResource(R.string.no_results_found, query)

    @Composable
    fun recentLocationsTitle(): String =
      stringResource(R.string.recent_locations_title)

    @Composable
    fun searchHint(): String =
      stringResource(R.string.search_hint)
  }
}
