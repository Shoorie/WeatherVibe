package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.domain.weather.model.DailyWeather
import com.weather.vibe.domain.weather.model.HourlyWeather
import com.weather.vibe.domain.weather.model.LocationResult
import com.weather.vibe.domain.weather.model.WeatherCondition.CLEAR_SKY
import com.weather.vibe.domain.weather.model.WeatherCondition.DRIZZLE
import com.weather.vibe.domain.weather.model.WeatherCondition.MAINLY_CLEAR
import com.weather.vibe.domain.weather.model.WeatherCondition.OVERCAST
import com.weather.vibe.domain.weather.model.WeatherCondition.PARTLY_CLOUDY
import com.weather.vibe.domain.weather.model.WeatherCondition.RAIN
import com.weather.vibe.domain.weather.model.WeatherData
import com.weather.vibe.feature.home.presentation.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.SearchState

internal class HomePreviewParameterProvider :
  PreviewParameterProvider<HomePreviewParams> {

  private val loadingState: HomePreviewParams =
    HomePreviewParams(state = Loading)

  private val errorState: HomePreviewParams =
    HomePreviewParams(state = Error("Brak połączenia z internetem."))

  private val successWithForecast: HomePreviewParams =
    HomePreviewParams(
      state = Loaded(
        WeatherData(
          cityName = "Zielona Góra",
          latitude = 51.9354,
          longitude = 15.5064,
          currentTemperature = 18.5,
          condition = PARTLY_CLOUDY,
          windSpeed = 12.0,
          windDirection = 225.0,
          humidity = 65,
          isDay = true,
          hourlyForecast = List(8) { i ->
            HourlyWeather(
              time = "2024-01-15T${14 + i}:00",
              temperature = 18.0 + i,
              condition = PARTLY_CLOUDY,
              humidity = 60,
              windSpeed = 12.0,
              precipitationProbability = 20
            )
          },
          dailyForecast = listOf(
            DailyWeather("2024-01-15", 22.0, 14.0, PARTLY_CLOUDY, 20),
            DailyWeather("2024-01-16", 19.0, 11.0, RAIN, 75),
            DailyWeather("2024-01-17", 15.0, 8.0, OVERCAST, 30),
            DailyWeather("2024-01-18", 24.0, 16.0, CLEAR_SKY, 5),
            DailyWeather("2024-01-19", 21.0, 13.0, MAINLY_CLEAR, 10),
            DailyWeather("2024-01-20", 17.0, 10.0, DRIZZLE, 60),
            DailyWeather("2024-01-21", 20.0, 12.0, PARTLY_CLOUDY, 25)
          )
        )
      )
    )

  private val successWithSearch: HomePreviewParams =
    HomePreviewParams(
      state = Loaded(
        WeatherData(
          cityName = "Toruń",
          latitude = 53.0138,
          longitude = 18.5984,
          currentTemperature = 18.5,
          condition = PARTLY_CLOUDY,
          windSpeed = 12.0,
          windDirection = 225.0,
          humidity = 65,
          isDay = true,
          hourlyForecast = emptyList(),
          dailyForecast = emptyList()
        )
      ),
      searchState = SearchState(
        isActive = true,
        query = "Wars",
        results = listOf(
          LocationResult(1L, "Warszawa", 52.229, 21.011, "Polska", "Mazowieckie"),
          LocationResult(2L, "Wrocław", 51.107, 17.038, "Polska", "Dolnośląskie")
        )
      )
    )

  override val values: Sequence<HomePreviewParams> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast,
      successWithSearch
    )
}
