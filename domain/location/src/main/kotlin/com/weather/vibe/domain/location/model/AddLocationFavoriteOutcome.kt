package com.weather.vibe.domain.location.model

sealed interface AddLocationFavoriteOutcome {
  data object Added : AddLocationFavoriteOutcome
  data object AlreadyExists : AddLocationFavoriteOutcome
  data object LimitReached : AddLocationFavoriteOutcome
}
