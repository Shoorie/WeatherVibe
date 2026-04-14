package com.weather.vibe.data.widget.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object PinnedWidgetCacheSerializer : Serializer<PinnedWidgetCacheData> {

  override val defaultValue: PinnedWidgetCacheData =
    PinnedWidgetCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): PinnedWidgetCacheData =
    try {
      PinnedWidgetCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: PinnedWidgetCacheData, output: OutputStream) =
    t.writeTo(output)
}
