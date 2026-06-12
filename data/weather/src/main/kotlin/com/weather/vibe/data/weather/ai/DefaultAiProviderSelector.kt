package com.weather.vibe.data.weather.ai

import com.weather.vibe.core.ai.AiProvider
import com.weather.vibe.core.ai.AiProvider.Companion.fromValue
import com.weather.vibe.core.ai.AiProviderSelector
import com.weather.vibe.domain.remoteconfig.proxy.RemoteConfigProxy
import org.koin.core.annotation.Single

@Single(binds = [AiProviderSelector::class])
internal class DefaultAiProviderSelector(
  private val proxy: RemoteConfigProxy
) : AiProviderSelector {

  override fun current(): AiProvider =
    fromValue(proxy.getString(AI_PROVIDER_KEY))

  private companion object {
    const val AI_PROVIDER_KEY = "ai_provider"
  }
}
