package com.weather.vibe.data.appearance.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object AppearanceCacheSerializer : Serializer<AppearanceCacheData> {

  override val defaultValue: AppearanceCacheData =
    AppearanceCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): AppearanceCacheData =
    try {
      AppearanceCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: AppearanceCacheData, output: OutputStream) =
    t.writeTo(output)
}
