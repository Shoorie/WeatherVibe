package com.weather.vibe.data.settings.persistence

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object UserSettingsCacheSerializer : Serializer<UserSettingsCacheData> {

  override val defaultValue: UserSettingsCacheData =
    UserSettingsCacheData.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): UserSettingsCacheData =
    try {
      UserSettingsCacheData.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }

  override suspend fun writeTo(t: UserSettingsCacheData, output: OutputStream) =
    t.writeTo(output)
}
