package com.weather.vibe.data.viberating.di

import android.content.Context
import androidx.room.Room
import com.weather.vibe.data.viberating.local.VibeRatingDatabase
import com.weather.vibe.data.viberating.local.dao.RatingDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
@ComponentScan("com.weather.vibe.data.viberating")
class DataVibeRatingModule {

  @Single
  internal fun provideDatabase(context: Context): VibeRatingDatabase =
    Room.databaseBuilder(
      context = context,
      klass = VibeRatingDatabase::class.java,
      name = "vibe_rating.db"
    )
      .fallbackToDestructiveMigration(dropAllTables = true)
      .build()

  @Single
  internal fun provideRatingDao(database: VibeRatingDatabase): RatingDao =
    database.ratingDao()
}
