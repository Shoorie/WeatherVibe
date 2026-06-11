package com.weather.vibe.data.alerts.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object AlertDedupeCacheSerializer : Serializer<AlertDedupeData> {

  override val defaultValue: AlertDedupeData = AlertDedupeData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): AlertDedupeData =
    try {
      AlertDedupeData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: AlertDedupeData, output: OutputStream) =
    t.writeTo(output)
}
