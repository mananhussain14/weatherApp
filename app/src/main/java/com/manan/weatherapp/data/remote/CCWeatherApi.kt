package com.manan.weatherapp.data.remote

import com.manan.weatherapp.data.remote.dto.CCForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CCWeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getFiveDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CCForecastResponseDto
}