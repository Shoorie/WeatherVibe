package com.weather.vibe.domain.weather.model

data class Coordinates(
  val name: String,
  val latitude: Double,
  val longitude: Double
) {

  val id: String
    get() = idOf(latitude, longitude)

  companion object {

    fun idOf(latitude: Double, longitude: Double): String =
      "$latitude$ID_SEPARATOR$longitude"

    fun parseId(id: String): Pair<Double, Double> {
      val parts = id.split(ID_SEPARATOR)
      val latitude = parts.getOrNull(0)?.toDoubleOrNull() ?: 0.0
      val longitude = parts.getOrNull(1)?.toDoubleOrNull() ?: 0.0
      return latitude to longitude
    }

    private const val ID_SEPARATOR = ","
  }
}
