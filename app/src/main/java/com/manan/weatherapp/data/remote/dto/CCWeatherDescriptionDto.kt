package com.manan.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class CCWeatherDescriptionDto(
    @Json(name = "main")
    val m_main: String,

    @Json(name = "description")
    val m_description: String?
)
