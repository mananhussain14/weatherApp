package com.manan.weatherapp.data.repository

import com.manan.weatherapp.BuildConfig
import com.manan.weatherapp.data.remote.CCWeatherApi
import com.manan.weatherapp.data.remote.CCWeatherApiProvider
import com.manan.weatherapp.data.remote.dto.CCForecastItemDto
import com.manan.weatherapp.domain.model.CCWeatherDayForecast
import com.manan.weatherapp.domain.model.WeatherCondition
import com.manan.weatherapp.domain.repository.CCWeatherRepository
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CCWeatherRepositoryImpl(
    private val m_api: CCWeatherApi = CCWeatherApiProvider.createApi()
) : CCWeatherRepository {

    override suspend fun getFiveDayForecast(
        lat: Double,
        lon: Double
    ): List<CCWeatherDayForecast> {

        val response = m_api.getFiveDayForecast(
            lat = lat,
            lon = lon,
            apiKey = BuildConfig.OPEN_WEATHER_API_KEY,
            units = "metric"
        )

        val groupedByDay = response.m_list.groupBy { it.toLocalDate() }

        return groupedByDay
            .toSortedMap()
            .entries
            .take(5)
            .map { (date, items) ->
                CCWeatherDayForecast(
                    m_date = date,
                    m_temperatureCelsius = items.map { it.m_main.m_temp }.average().toInt(),
                    m_condition = pickCondition(items)
                )
            }
    }

    private fun CCForecastItemDto.toLocalDate(): LocalDate {
        val instant = Instant.ofEpochSecond(m_dt)
        return instant.atZone(ZoneId.systemDefault()).toLocalDate()
    }

    private fun pickCondition(items: List<CCForecastItemDto>): WeatherCondition {
        val main = items
            .mapNotNull { it.m_weather.firstOrNull()?.m_main }
            .firstOrNull()
            ?: return WeatherCondition.CLOUDY

        return mapApiConditionToDomain(main)
    }

    private fun mapApiConditionToDomain(value: String): WeatherCondition {
        return when (value.lowercase()) {
            "clear" -> WeatherCondition.SUNNY
            "rain", "drizzle", "thunderstorm" -> WeatherCondition.RAINY
            "clouds" -> WeatherCondition.CLOUDY
            "mist", "fog", "smoke", "haze" -> WeatherCondition.FOREST
            else -> WeatherCondition.CLOUDY
        }
    }
}
