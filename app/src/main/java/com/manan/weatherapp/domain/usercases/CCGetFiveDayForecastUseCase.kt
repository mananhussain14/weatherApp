package com.manan.weatherapp.domain.usercases

import com.manan.weatherapp.domain.repository.CCWeatherRepository
import com.manan.weatherapp.domain.model.CCWeatherDayForecast

class CCGetFiveDayForecastUseCase(
    private val m_repository: CCWeatherRepository
) {

    suspend operator fun invoke(
        lat: Double,
        lon: Double
    ): List<CCWeatherDayForecast> {
        return m_repository.getFiveDayForecast(lat, lon)
    }
}