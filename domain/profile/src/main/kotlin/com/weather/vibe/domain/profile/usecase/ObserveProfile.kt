package com.weather.vibe.domain.profile.usecase

import com.weather.vibe.core.time.TimeProvider
import com.weather.vibe.domain.profile.cache.ProfileCache
import com.weather.vibe.domain.profile.model.Profile
import com.weather.vibe.domain.profile.model.ProfileSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveProfile internal constructor(
  private val cache: ProfileCache,
  private val time: TimeProvider
) {

  operator fun invoke(): Flow<ProfileSummary> =
    cache.observeProfile()
      .map(::toSummary)

  private suspend fun toSummary(profile: Profile): ProfileSummary =
    ProfileSummary(
      username = profile.username,
      usageDays = calculateUsageDays(resolveInstalledAt(profile))
    )

  private suspend fun resolveInstalledAt(profile: Profile): Long =
    profile.installedAtMillis.takeIf { it != NEVER }
      ?: bootstrapInstalledAt()

  private suspend fun bootstrapInstalledAt(): Long {
    val now = time.nowEpochMillis()
    cache.saveInstalledAtMillis(now)
    return now
  }

  private fun calculateUsageDays(installedAt: Long): Int {
    val elapsed = time.nowEpochMillis() - installedAt
    return (elapsed / MILLIS_PER_DAY).toInt() + FIRST_DAY
  }

  private companion object {
    const val MILLIS_PER_DAY: Long = 24L * 60 * 60 * 1000
    const val NEVER: Long = 0L
    const val FIRST_DAY: Int = 1
  }
}
