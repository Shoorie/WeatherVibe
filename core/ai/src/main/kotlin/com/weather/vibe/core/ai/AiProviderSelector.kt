package com.weather.vibe.core.ai

interface AiProviderSelector {
  fun current(): AiProvider
}
