package com.weather.vibe.navigation

import androidx.navigation3.runtime.NavKey
import com.weather.vibe.navigation.home.HomeRoute
import com.weather.vibe.navigation.locations.AddLocationFavoriteRoute
import com.weather.vibe.navigation.locations.LocationsRoute
import com.weather.vibe.navigation.onboarding.LocationOnboardingRoute
import com.weather.vibe.navigation.onboarding.WelcomeOnboardingRoute
import com.weather.vibe.navigation.planner.ActivityPlannerRoute
import com.weather.vibe.navigation.profile.ProfileLicensesRoute
import com.weather.vibe.navigation.profile.ProfileNotificationsRoute
import com.weather.vibe.navigation.profile.ProfilePersonalizationRoute
import com.weather.vibe.navigation.profile.ProfileRoute
import com.weather.vibe.navigation.search.SearchRoute
import com.weather.vibe.navigation.viberating.VibeHistoryRoute
import com.weather.vibe.navigation.weather.WeatherDetailsRoute

internal fun NavKey.analyticsScreenName(): String? =
  when (this) {
    is HomeRoute -> "home"
    is WeatherDetailsRoute -> "weather_details"
    is ActivityPlannerRoute -> "activity_planner"
    is SearchRoute -> "search"
    is LocationsRoute -> "locations"
    is AddLocationFavoriteRoute -> "add_location_favorite"
    is ProfileRoute -> "profile"
    is VibeHistoryRoute -> "vibe_history"
    is ProfilePersonalizationRoute -> "personalization"
    is ProfileNotificationsRoute -> "notifications_settings"
    is ProfileLicensesRoute -> "licenses"
    is WelcomeOnboardingRoute -> "welcome_onboarding"
    is LocationOnboardingRoute -> "location_onboarding"
    else -> null
  }
