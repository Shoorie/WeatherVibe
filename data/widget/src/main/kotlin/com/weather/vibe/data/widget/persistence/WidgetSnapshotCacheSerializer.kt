package com.weather.vibe.data.widget.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object WidgetSnapshotCacheSerializer : Serializer<WidgetSnapshotCacheData> {

  override val defaultValue: WidgetSnapshotCacheData =
    WidgetSnapshotCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): WidgetSnapshotCacheData =
    try {
      WidgetSnapshotCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: WidgetSnapshotCacheData, output: OutputStream) =
    t.writeTo(output)
}
