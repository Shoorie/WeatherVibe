package com.weather.vibe.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weather.vibe.feature.home.R

object HomeWidgetPromoTexts {

  @Composable
  fun title(): String =
    stringResource(R.string.widget_promo_title)

  @Composable
  fun subtitle(): String =
    stringResource(R.string.widget_promo_subtitle)

  @Composable
  fun previewLocation(): String =
    stringResource(R.string.widget_promo_preview_location)

  @Composable
  fun previewCondition(): String =
    stringResource(R.string.widget_promo_preview_condition)

  @Composable
  fun previewMood(): String =
    stringResource(R.string.widget_promo_preview_mood)

  @Composable
  fun previewTemperature(): String =
    stringResource(R.string.widget_promo_preview_temperature)

  @Composable
  fun previewEmoji(): String =
    stringResource(R.string.widget_promo_preview_emoji)

  @Composable
  fun previewFetchedAt(): String =
    stringResource(R.string.widget_promo_preview_fetched_at)

  @Composable
  fun primaryAction(): String =
    stringResource(R.string.widget_promo_primary_action)

  @Composable
  fun secondaryAction(): String =
    stringResource(R.string.widget_promo_secondary_action)
}
