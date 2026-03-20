package com.weather.vibe.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.vibe.domain.usecase.GetWeatherUseCase
import com.weather.vibe.domain.usecase.SearchLocationUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class HomeViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val searchLocationUseCase: SearchLocationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        loadWeather()
        observeSearchQuery()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RefreshWeather -> loadWeather()
            is HomeEvent.ToggleSearch -> openSearch()
            is HomeEvent.DismissSearch -> dismissSearch()
            is HomeEvent.SearchQueryChanged -> onSearchQueryChanged(event.query)
            is HomeEvent.LocationSelected -> {
                val displayName = buildDisplayName(event.result.name, event.result.admin1)
                dismissSearch()
                loadWeather(event.result.latitude, event.result.longitude, displayName)
            }
        }
    }

    private fun openSearch() {
        _state.update { it.copy(isSearchActive = true, searchQuery = "", searchResults = emptyList()) }
        searchQueryFlow.value = ""
    }

    private fun dismissSearch() {
        _state.update { it.copy(isSearchActive = false, searchQuery = "", searchResults = emptyList(), isSearching = false) }
        searchQueryFlow.value = ""
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchQueryFlow.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(400L)
                .filter { it.length >= 2 }
                .collectLatest { query -> performSearch(query) }
        }
    }

    private suspend fun performSearch(query: String) {
        _state.update { it.copy(isSearching = true) }
        searchLocationUseCase(query)
            .onSuccess { results ->
                _state.update { it.copy(isSearching = false, searchResults = results) }
            }
            .onFailure {
                _state.update { it.copy(isSearching = false, searchResults = emptyList()) }
            }
    }

    private fun loadWeather(
        latitude: Double = DEFAULT_LATITUDE,
        longitude: Double = DEFAULT_LONGITUDE,
        cityName: String = DEFAULT_CITY
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getWeatherUseCase(latitude, longitude, cityName)
                .onSuccess { data ->
                    _state.update { it.copy(isLoading = false, weatherData = data) }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = error.message ?: "Unexpected error")
                    }
                }
        }
    }

    private fun buildDisplayName(name: String, admin1: String?): String = buildString {
        append(name)
        if (admin1 != null) append(", $admin1")
    }

    private companion object {
        const val DEFAULT_LATITUDE = 53.0138
        const val DEFAULT_LONGITUDE = 18.5984
        const val DEFAULT_CITY = "Toruń"
    }
}
