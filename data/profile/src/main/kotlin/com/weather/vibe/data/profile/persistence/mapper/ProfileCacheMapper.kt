package com.weather.vibe.data.profile.persistence.mapper

import com.weather.vibe.data.profile.persistence.ProfileCacheData
import com.weather.vibe.domain.profile.model.Profile
import org.koin.core.annotation.Factory

@Factory
internal class ProfileCacheMapper {

  fun toDomain(cacheData: ProfileCacheData): Profile =
    Profile(
      username = cacheData.username,
      installedAtMillis = cacheData.installedAtMillis
    )
}
