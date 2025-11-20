package com.manan.weatherapp.ui.forecast

import com.manan.weatherapp.domain.model.CCWeatherDayForecast

sealed class CCForecastUiState {
    object Loading : CCForecastUiState()
    data class Success(val m_items: List<CCWeatherDayForecast>) : CCForecastUiState()
    data class Error(val m_message: String) : CCForecastUiState()
    object NoLocationPermission : CCForecastUiState()
}