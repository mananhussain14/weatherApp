package com.manan.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class CCForecastItemDto(
    @Json(name = "dt")
    val m_dt: Long,

    @Json(name = "main")
    val m_main: CCMainDto,

    @Json(name = "weather")
    val m_weather: List<CCWeatherDescriptionDto>
)