package com.weather.vibe.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.vibe.domain.model.DailyWeather
import com.weather.vibe.domain.model.HourlyWeather
import com.weather.vibe.domain.model.LocationResult
import com.weather.vibe.domain.model.WeatherCondition
import com.weather.vibe.domain.model.WeatherData
import com.weather.vibe.feature.home.components.CurrentWeatherSection
import com.weather.vibe.feature.home.components.DailyForecastList
import com.weather.vibe.feature.home.components.HourlyForecastRow
import com.weather.vibe.feature.home.components.LocationSearchBar
import com.weather.vibe.feature.home.components.LocationSearchResults
import com.weather.vibe.feature.home.components.WeatherMetricsGrid
import com.weather.vibe.ui.theme.AppDimens
import com.weather.vibe.ui.theme.BackgroundGradientEnd
import com.weather.vibe.ui.theme.BackgroundGradientStart
import com.weather.vibe.ui.theme.WeatherVibeTheme
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val backgroundBrush = Brush.verticalGradient(listOf(BackgroundGradientStart, BackgroundGradientEnd))

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                error = state.error,
                onRetry = { onEvent(HomeEvent.RefreshWeather) }
            )
            state.weatherData != null -> WeatherContent(
                weatherData = state.weatherData,
                onRefresh = { onEvent(HomeEvent.RefreshWeather) },
                onSearchToggle = { onEvent(HomeEvent.ToggleSearch) }
            )
        }

        if (state.isSearchActive) {
            SearchOverlay(
                query = state.searchQuery,
                results = state.searchResults,
                isSearching = state.isSearching,
                onQueryChange = { onEvent(HomeEvent.SearchQueryChanged(it)) },
                onLocationSelected = { onEvent(HomeEvent.LocationSelected(it)) },
                onDismiss = { onEvent(HomeEvent.DismissSearch) }
            )
        }
    }
}

@Composable
private fun WeatherContent(
    modifier: Modifier = Modifier,
    weatherData: WeatherData,
    onRefresh: () -> Unit,
    onSearchToggle: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = AppDimens.PaddingMedium)
    ) {
        item {
            LocationHeader(
                cityName = weatherData.cityName,
                onRefresh = onRefresh,
                onSearchToggle = onSearchToggle
            )
        }
        item { CurrentWeatherSection(weatherData = weatherData) }
        item { HourlyForecastRow(hourlyForecasts = weatherData.hourlyForecast) }
        item { Spacer(modifier = Modifier.height(AppDimens.PaddingSmall)) }
        item { DailyForecastList(dailyForecasts = weatherData.dailyForecast) }
        item { Spacer(modifier = Modifier.height(AppDimens.PaddingSmall)) }
        item {
            WeatherMetricsGrid(
                humidity = weatherData.humidity,
                windSpeed = weatherData.windSpeed,
                windDirection = weatherData.windDirection,
                precipitationProbability = weatherData.hourlyForecast
                    .firstOrNull()?.precipitationProbability ?: 0
            )
        }
        item { Spacer(modifier = Modifier.height(AppDimens.PaddingExtraLarge)) }
    }
}

@Composable
private fun LocationHeader(
    modifier: Modifier = Modifier,
    cityName: String,
    onRefresh: () -> Unit,
    onSearchToggle: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = AppDimens.PaddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = currentDateLabel(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onSearchToggle) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Szukaj miasta",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Odśwież",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SearchOverlay(
    modifier: Modifier = Modifier,
    query: String,
    results: List<LocationResult>,
    isSearching: Boolean,
    onQueryChange: (String) -> Unit,
    onLocationSelected: (LocationResult) -> Unit,
    onDismiss: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundBrush = Brush.verticalGradient(listOf(BackgroundGradientStart, BackgroundGradientEnd))

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(AppDimens.PaddingMedium)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {}
                )
        ) {
            LocationSearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onDismiss = onDismiss
            )
            Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))
            when {
                isSearching -> Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(AppDimens.PaddingMedium)
                    )
                }
                results.isNotEmpty() -> LocationSearchResults(
                    results = results,
                    onLocationSelected = onLocationSelected
                )
                query.length >= 2 -> Text(
                    text = "Nie znaleziono wyników dla \"$query\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimens.PaddingMedium),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "⚡", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(AppDimens.PaddingMedium))
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = AppDimens.PaddingLarge)
        )
        Spacer(modifier = Modifier.height(AppDimens.PaddingLarge))
        IconButton(onClick = onRetry) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Spróbuj ponownie",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun currentDateLabel(): String = runCatching {
    LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH))
}.getOrDefault("")

@Preview(showBackground = true)
@Composable
private fun HomeContentLoadingPreview() {
    WeatherVibeTheme {
        HomeContent(state = HomeState(isLoading = true), onEvent = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentErrorPreview() {
    WeatherVibeTheme {
        HomeContent(
            state = HomeState(error = "Brak połączenia z internetem."),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentSearchPreview() {
    WeatherVibeTheme {
        HomeContent(
            state = HomeState(
                isSearchActive = true,
                searchQuery = "Wars",
                searchResults = listOf(
                    LocationResult(1L, "Warszawa", 52.229, 21.011, "Polska", "Mazowieckie"),
                    LocationResult(2L, "Wrocław", 51.107, 17.038, "Polska", "Dolnośląskie")
                )
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentDataPreview() {
    WeatherVibeTheme {
        HomeContent(
            state = HomeState(
                weatherData = WeatherData(
                    cityName = "Zielona Góra",
                    latitude = 51.9354,
                    longitude = 15.5064,
                    currentTemperature = 18.5,
                    condition = WeatherCondition.PARTLY_CLOUDY,
                    windSpeed = 12.0,
                    windDirection = 225.0,
                    humidity = 65,
                    isDay = true,
                    hourlyForecast = List(8) { i ->
                        HourlyWeather(
                            time = "2024-01-15T${14 + i}:00",
                            temperature = 18.0 + i,
                            condition = WeatherCondition.PARTLY_CLOUDY,
                            humidity = 60,
                            windSpeed = 12.0,
                            precipitationProbability = 20
                        )
                    },
                    dailyForecast = listOf(
                        DailyWeather("2024-01-15", 22.0, 14.0, WeatherCondition.PARTLY_CLOUDY, 20),
                        DailyWeather("2024-01-16", 19.0, 11.0, WeatherCondition.RAIN, 75),
                        DailyWeather("2024-01-17", 15.0, 8.0, WeatherCondition.OVERCAST, 30),
                        DailyWeather("2024-01-18", 24.0, 16.0, WeatherCondition.CLEAR_SKY, 5),
                        DailyWeather("2024-01-19", 21.0, 13.0, WeatherCondition.MAINLY_CLEAR, 10),
                        DailyWeather("2024-01-20", 17.0, 10.0, WeatherCondition.DRIZZLE, 60),
                        DailyWeather("2024-01-21", 20.0, 12.0, WeatherCondition.PARTLY_CLOUDY, 25)
                    )
                )
            ),
            onEvent = {}
        )
    }
}
