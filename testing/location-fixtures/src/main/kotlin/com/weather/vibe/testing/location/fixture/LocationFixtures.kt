package com.weather.vibe.testing.location.fixture

import com.weather.vibe.domain.location.model.Location

object LocationFixtures {

  const val ID = 1L
  const val NAME = "Warsaw"
  const val ADMIN1 = "Mazowieckie"
  const val COUNTRY = "Poland"
  const val LATITUDE = 52.23
  const val LONGITUDE = 21.01

  val WARSAW: Location = location()

  val KRAKOW: Location = location(
    id = 2L,
    name = "Kraków",
    admin1 = "Małopolskie",
    latitude = 50.064,
    longitude = 19.944
  )

  val GDANSK: Location = location(
    id = 3L,
    name = "Gdańsk",
    admin1 = "Pomorskie",
    latitude = 54.352,
    longitude = 18.646
  )

  fun location(
    id: Long = ID,
    name: String = NAME,
    admin1: String? = ADMIN1,
    country: String = COUNTRY,
    latitude: Double = LATITUDE,
    longitude: Double = LONGITUDE
  ): Location = Location(
    id = id,
    name = name,
    admin1 = admin1,
    country = country,
    latitude = latitude,
    longitude = longitude
  )
}
