package com.weather.vibe.domain.ads.placement

import com.weather.vibe.domain.ads.placement.AdPlacementKeys.ACTIVITY_PLANNER_BOTTOM
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.BRIEF_REFRESH_REWARDED
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.HOME_BOTTOM
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.LOCATIONS_BOTTOM
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.SEARCH_BOTTOM
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.TONE_UNLOCK_REWARDED
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.VIBE_HISTORY_BOTTOM
import com.weather.vibe.domain.ads.placement.AdPlacementKeys.WEATHER_DETAILS_BOTTOM

enum class AdPlacement(val key: String) {
  ActivityPlannerBottom(key = ACTIVITY_PLANNER_BOTTOM),
  BriefRefreshRewarded(key = BRIEF_REFRESH_REWARDED),
  HomeBottom(key = HOME_BOTTOM),
  LocationsBottom(key = LOCATIONS_BOTTOM),
  SearchBottom(key = SEARCH_BOTTOM),
  ToneUnlockRewarded(key = TONE_UNLOCK_REWARDED),
  VibeHistoryBottom(key = VIBE_HISTORY_BOTTOM),
  WeatherDetailsBottom(key = WEATHER_DETAILS_BOTTOM)
}
