package com.weather.vibe.data.profile.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object ProfileCacheSerializer : Serializer<ProfileCacheData> {

  override val defaultValue: ProfileCacheData = ProfileCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): ProfileCacheData =
    try {
      ProfileCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: ProfileCacheData, output: OutputStream) =
    t.writeTo(output)
}
