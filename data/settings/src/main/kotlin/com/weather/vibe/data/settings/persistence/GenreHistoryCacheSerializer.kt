package com.weather.vibe.data.settings.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object GenreHistoryCacheSerializer : Serializer<GenreHistoryCacheData> {

  override val defaultValue: GenreHistoryCacheData =
    GenreHistoryCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): GenreHistoryCacheData =
    try {
      GenreHistoryCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: GenreHistoryCacheData, output: OutputStream) =
    t.writeTo(output)
}
