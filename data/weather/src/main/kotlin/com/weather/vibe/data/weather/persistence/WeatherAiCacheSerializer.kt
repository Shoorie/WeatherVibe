package com.weather.vibe.data.weather.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object WeatherAiCacheSerializer : Serializer<WeatherAiCacheData> {

  override val defaultValue: WeatherAiCacheData = WeatherAiCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): WeatherAiCacheData =
    try {
      WeatherAiCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: WeatherAiCacheData, output: OutputStream) =
    t.writeTo(output)
}
