package com.weather.vibe.data.ads.resources

import android.content.Context
import com.weather.vibe.data.ads.R
import com.weather.vibe.domain.ads.placement.AdPlacement
import com.weather.vibe.domain.ads.placement.AdPlacement.ActivityPlannerBottom
import com.weather.vibe.domain.ads.placement.AdPlacement.HomeBottom
import com.weather.vibe.domain.ads.placement.AdPlacement.LocationsBottom
import com.weather.vibe.domain.ads.placement.AdPlacement.SearchBottom
import com.weather.vibe.domain.ads.placement.AdPlacement.VibeHistoryBottom
import com.weather.vibe.domain.ads.placement.AdPlacement.WeatherDetailsBottom
import org.koin.core.annotation.Factory

@Factory
internal class AdUnitResources(private val context: Context) {

  fun idFor(placement: AdPlacement): String =
    context.getString(resourceFor(placement))

  private fun resourceFor(placement: AdPlacement): Int = when (placement) {
    ActivityPlannerBottom -> R.string.admob_activity_planner_bottom_id
    HomeBottom -> R.string.admob_home_bottom_id
    LocationsBottom -> R.string.admob_locations_bottom_id
    SearchBottom -> R.string.admob_search_bottom_id
    VibeHistoryBottom -> R.string.admob_viberating_history_bottom_id
    WeatherDetailsBottom -> R.string.admob_weather_details_bottom_id
  }
}
