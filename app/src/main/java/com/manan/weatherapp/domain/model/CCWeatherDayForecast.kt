package com.manan.weatherapp.domain.model

import java.time.LocalDate

data class CCWeatherDayForecast(
    val m_date: LocalDate,
    val m_temperatureCelsius: Int,
    val m_condition: WeatherCondition
)
