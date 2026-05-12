package com.weather.vibe.data.remoteconfig.proxy

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.weather.vibe.domain.remoteconfig.proxy.RemoteConfigProxy
import org.koin.core.annotation.Single

@Single(binds = [RemoteConfigProxy::class])
internal class FirebaseRemoteConfigProxy(
  private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigProxy {

  override fun getBoolean(key: String): Boolean =
    remoteConfig.getBoolean(key)

  override fun getLong(key: String): Long =
    remoteConfig.getLong(key)

  override fun getString(key: String): String =
    remoteConfig.getString(key)
}
