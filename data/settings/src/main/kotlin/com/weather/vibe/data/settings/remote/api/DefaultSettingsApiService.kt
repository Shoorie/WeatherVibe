package com.weather.vibe.data.settings.remote.api

import com.weather.vibe.data.settings.remote.dto.SettingsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Single

@Single(binds = [SettingsApiService::class])
internal class DefaultSettingsApiService(
  private val client: HttpClient
) : SettingsApiService {

  override suspend fun fetchSettingsItems(): List<SettingsResponse> =
    client.get("settingss").body()
}

