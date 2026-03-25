package com.weather.vibe.domain.settings.usecase

import com.weather.vibe.domain.settings.model.SettingsItem
import com.weather.vibe.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class FetchSettingsData(private val repository: SettingsRepository) {

  operator fun invoke(): Flow<Result<List<SettingsItem>>> =
    flow {
      val result = repository.fetchSettingsItems()
      emit(Result.success(result))
    }
      .catch { emit(Result.failure(it)) }
}

