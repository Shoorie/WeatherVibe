package com.weather.vibe.data.premium.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object PremiumStateCacheSerializer : Serializer<PremiumStateCacheData> {

  override val defaultValue: PremiumStateCacheData =
    PremiumStateCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): PremiumStateCacheData =
    try {
      PremiumStateCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: PremiumStateCacheData, output: OutputStream) =
    t.writeTo(output)
}
