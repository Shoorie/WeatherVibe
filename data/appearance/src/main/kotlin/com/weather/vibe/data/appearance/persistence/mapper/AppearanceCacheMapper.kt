package com.weather.vibe.data.appearance.persistence.mapper

import com.weather.vibe.data.appearance.persistence.ThemeModeProto
import com.weather.vibe.data.appearance.persistence.ThemeModeProto.THEME_MODE_AUTO
import com.weather.vibe.data.appearance.persistence.ThemeModeProto.THEME_MODE_DARK
import com.weather.vibe.data.appearance.persistence.ThemeModeProto.THEME_MODE_LIGHT
import com.weather.vibe.data.appearance.persistence.ThemeModeProto.UNRECOGNIZED
import com.weather.vibe.domain.appearance.model.ThemeMode
import com.weather.vibe.domain.appearance.model.ThemeMode.AUTO
import com.weather.vibe.domain.appearance.model.ThemeMode.DARK
import com.weather.vibe.domain.appearance.model.ThemeMode.LIGHT
import org.koin.core.annotation.Factory

@Factory
internal class AppearanceCacheMapper {

  fun toDomain(proto: ThemeModeProto): ThemeMode =
    when (proto) {
      THEME_MODE_LIGHT -> LIGHT
      THEME_MODE_DARK -> DARK
      THEME_MODE_AUTO, UNRECOGNIZED -> AUTO
    }

  fun toProto(mode: ThemeMode): ThemeModeProto =
    when (mode) {
      LIGHT -> THEME_MODE_LIGHT
      DARK -> THEME_MODE_DARK
      AUTO -> THEME_MODE_AUTO
    }
}
