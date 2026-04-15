package com.weather.vibe.navigation

import android.content.Intent
import androidx.navigation3.runtime.NavKey
import com.weather.vibe.core.navigation.deeplink.DeepLink
import com.weather.vibe.core.navigation.deeplink.DeepLink.Home
import com.weather.vibe.domain.location.usecase.GetLocationById
import org.koin.core.annotation.Factory

@Factory
internal class DeepLinkRouteResolver(
  private val getLocationById: GetLocationById
) {

  suspend fun resolve(intent: Intent?): NavKey =
    when (val link = DeepLink.parse(intent?.data)) {
      is Home -> resolveHome(link)
      null -> SplashRoute
    }

  private suspend fun resolveHome(link: Home): NavKey {
    val locationId = link.locationId ?: return SplashRoute
    val location = getLocationById(locationId) ?: return SplashRoute
    return HomeRoute(selectedLocation = location)
  }
}
