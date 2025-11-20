package com.manan.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class CCMainDto(
    @Json(name = "temp")
    val m_temp: Double
)