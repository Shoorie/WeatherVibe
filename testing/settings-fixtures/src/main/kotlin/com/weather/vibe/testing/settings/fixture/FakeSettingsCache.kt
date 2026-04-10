package com.weather.vibe.testing.settings.fixture

import com.weather.vibe.domain.settings.cache.SettingsCache
import com.weather.vibe.domain.settings.model.UserSettings
import com.weather.vibe.testing.settings.fixture.UserSettingsFixtures.DEFAULT_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FakeSettingsCache(
  initial: UserSettings = DEFAULT_SETTINGS
) : SettingsCache {

  private val state = MutableStateFlow(initial)

  var readError: Throwable? = null
  var writeError: Throwable? = null

  val current: UserSettings
    get() = state.value

  override fun get(): Flow<UserSettings> = flow {
    readError?.let { throw it }
    emitAll(state)
  }

  override suspend fun save(settings: UserSettings) {
    throwIfWriteFails()
    state.update { settings }
  }

  override suspend fun update(change: (UserSettings) -> UserSettings) {
    throwIfWriteFails()
    state.update(change)
  }

  private fun throwIfWriteFails() {
    writeError?.let { throw it }
  }
}
