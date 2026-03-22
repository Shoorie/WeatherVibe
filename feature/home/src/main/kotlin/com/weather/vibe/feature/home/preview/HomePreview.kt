package com.weather.vibe.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Error
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loaded
import com.weather.vibe.feature.home.presentation.state.HomeUiState.Loading
import com.weather.vibe.feature.home.presentation.state.CurrentWeatherUiState
import com.weather.vibe.feature.home.presentation.state.DailyForecastUiState
import com.weather.vibe.feature.home.presentation.state.HeaderUiState
import com.weather.vibe.feature.home.presentation.state.HourlyForecastUiState
import com.weather.vibe.feature.home.presentation.state.MetricsUiState
import com.weather.vibe.feature.home.presentation.state.SunriseSunsetUiState
import com.weather.vibe.feature.home.preview.params.HomePreviewParams

internal class HomePreview :
  PreviewParameterProvider<HomePreviewParams> {

  private val loadingState: HomePreviewParams =
    HomePreviewParams(state = Loading)

  private val errorState: HomePreviewParams =
    HomePreviewParams(
      state = Error("Brak połączenia z internetem.")
    )

  private val successWithForecast: HomePreviewParams =
    HomePreviewParams(
      state = Loaded(
        currentWeather = CurrentWeatherUiState(
          conditionEmoji = "⛅",
          conditionLabel = "Partly Cloudy",
          currentTemperature = "19°",
          feelsLikeTemperature = "17°",
          highTemperature = "22°",
          lowTemperature = "14°"
        ),
        dailyForecast = listOf(
          DailyForecastUiState("⛅", "Today", "22°", "14°"),
          DailyForecastUiState("🌧️", "Tue", "19°", "11°"),
          DailyForecastUiState("☁️", "Wed", "15°", "8°"),
          DailyForecastUiState("☀️", "Thu", "24°", "16°"),
          DailyForecastUiState("🌤️", "Fri", "21°", "13°"),
          DailyForecastUiState("🌦️", "Sat", "17°", "10°"),
          DailyForecastUiState("⛅", "Sun", "20°", "12°")
        ),
        header = HeaderUiState(
          cityName = "Zielona Góra",
          dateLabel = "Saturday, 21 March"
        ),
        hourlyForecast = List(8) { i ->
          HourlyForecastUiState(
            conditionEmoji = "⛅",
            isCurrentHour = i == 0,
            temperature = "${18 + i}°",
            timeLabel = "${14 + i}:00"
          )
        },
        metrics = MetricsUiState(
          cloudCoverValue = "45%",
          dewPointValue = "12°",
          humidityValue = "65%",
          precipitationAmountValue = "0.2 mm",
          precipitationValue = "20%",
          pressureValue = "1015 hPa",
          uvIndexValue = "3.5",
          visibilityValue = "24 km",
          windDirectionValue = "SW",
          windGustsValue = "20 km/h",
          windSpeedMaxValue = "28 km/h",
          windSpeedValue = "12 km/h"
        ),
        sunriseSunset = SunriseSunsetUiState(
          sunriseTime = "06:24",
          sunsetTime = "18:07"
        )
      )
    )

  override val values: Sequence<HomePreviewParams> =
    sequenceOf(
      loadingState,
      errorState,
      successWithForecast
    )
}
