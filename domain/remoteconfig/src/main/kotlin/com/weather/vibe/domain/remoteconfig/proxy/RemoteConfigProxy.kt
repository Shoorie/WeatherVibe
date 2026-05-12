package com.weather.vibe.domain.remoteconfig.proxy

interface RemoteConfigProxy {

  fun getBoolean(key: String): Boolean

  fun getLong(key: String): Long

  fun getString(key: String): String
}
