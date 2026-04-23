package com.weather.vibe.feature.search.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.search.R
import org.koin.core.annotation.Factory

@Factory
internal class SearchResources(private val context: Context) {

  fun defaultError(): String =
    context.getString(R.string.search_error_default)

  object Emojis {
    fun clock(): String = "\uD83D\uDD58"
    fun error(): String = "\u26A1"
    fun globe(): String = "\uD83C\uDF0D"
    fun locationPin(): String = "\uD83D\uDCCD"
    fun telescope(): String = "\uD83D\uDD2D"
  }

  object Texts {

    @Composable
    fun clearContentDescription(): String =
      stringResource(R.string.search_clear_content_description)

    @Composable
    fun emptySubtitle(query: String): String =
      stringResource(R.string.search_empty_subtitle, query)

    @Composable
    fun emptyTitle(): String =
      stringResource(R.string.search_empty_title)

    @Composable
    fun errorTitle(): String =
      stringResource(R.string.search_error_title)

    @Composable
    fun idleSubtitle(): String =
      stringResource(R.string.search_idle_subtitle)

    @Composable
    fun idleTitle(): String =
      stringResource(R.string.search_idle_title)

    @Composable
    fun recentsSubtitle(): String =
      stringResource(R.string.search_section_recents_subtitle)

    @Composable
    fun recentsTitle(): String =
      stringResource(R.string.search_section_recents_title)

    @Composable
    fun resultsSubtitle(): String =
      stringResource(R.string.search_section_results_subtitle)

    @Composable
    fun resultsTitle(): String =
      stringResource(R.string.search_section_results_title)

    @Composable
    fun retry(): String =
      stringResource(R.string.search_retry)

    @Composable
    fun searchHint(): String =
      stringResource(R.string.search_hint)

    @Composable
    fun favoritesCapacity(used: Int, limit: Int): String =
      stringResource(R.string.search_favorites_capacity, used, limit)

    @Composable
    fun favoritesCapacityFull(limit: Int): String =
      stringResource(R.string.search_favorites_capacity_full, limit)

    @Composable
    fun heartAddContentDescription(): String =
      stringResource(R.string.search_heart_add_content_description)

    @Composable
    fun heartRemoveContentDescription(): String =
      stringResource(R.string.search_heart_remove_content_description)

    @Composable
    fun heartStateAdded(): String =
      stringResource(R.string.search_heart_state_added)

    @Composable
    fun heartStateNotAdded(): String =
      stringResource(R.string.search_heart_state_not_added)
  }
}
