package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.cache.SettingsCache
import com.weather.vibe.domain.settings.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@Factory
class GetUserSettings internal constructor(
  private val cache: SettingsCache
) {

  operator fun invoke(): Flow<Result<UserSettings>> =
    cache.get()
      .map { success(it) }
      .catch { emit(failure(it)) }
}
