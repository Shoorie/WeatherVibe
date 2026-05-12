package com.weather.vibe.domain.remoteconfig

import kotlinx.coroutines.flow.Flow

interface RemoteConfigActivations {
  val activations: Flow<Unit>
}
