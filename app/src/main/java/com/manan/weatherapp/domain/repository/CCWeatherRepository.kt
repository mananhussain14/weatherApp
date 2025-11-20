package com.manan.weatherapp.domain.repository

import com.manan.weatherapp.domain.model.CCWeatherDayForecast

interface CCWeatherRepository {
    suspend fun getFiveDayForecast(
        lat: Double,
        lon: Double
    ): List<CCWeatherDayForecast>
}