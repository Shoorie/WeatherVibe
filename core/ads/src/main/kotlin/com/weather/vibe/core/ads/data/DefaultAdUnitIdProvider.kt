package com.weather.vibe.core.ads.data

import android.content.Context
import com.weather.vibe.core.ads.R
import com.weather.vibe.core.ads.domain.AdPlacement
import org.koin.core.annotation.Single

@Single(binds = [AdUnitIdProvider::class])
internal class DefaultAdUnitIdProvider(private val context: Context) : AdUnitIdProvider {

  override fun idFor(placement: AdPlacement): String =
    context.getString(resourceFor(placement))

  private fun resourceFor(placement: AdPlacement): Int = when (placement) {
    AdPlacement.HomeBottom -> R.string.admob_home_bottom_id
    AdPlacement.LocationsBottom -> R.string.admob_locations_bottom_id
    AdPlacement.ActivityPlannerBottom -> R.string.admob_activity_planner_bottom_id
    AdPlacement.SearchBottom -> R.string.admob_search_bottom_id
    AdPlacement.VibeHistoryBottom -> R.string.admob_viberating_history_bottom_id
    AdPlacement.WeatherDetailsBottom -> R.string.admob_weather_details_bottom_id
  }
}
