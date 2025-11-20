package com.manan.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class CCForecastResponseDto(
    @Json(name = "list")
    val m_list: List<CCForecastItemDto>
)






