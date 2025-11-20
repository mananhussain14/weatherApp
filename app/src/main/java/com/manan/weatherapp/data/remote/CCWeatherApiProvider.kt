package com.manan.weatherapp.data.remote



import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CCWeatherApiProvider {

    private const val BASE_URL = "https://api.openweathermap.org/"

    fun createApi(): CCWeatherApi {
        val m_moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val m_client = createOkHttpClient()

        val m_retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(m_client)
            .addConverterFactory(MoshiConverterFactory.create(m_moshi))
            .build()

        return m_retrofit.create(CCWeatherApi::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val m_logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(m_logging)
            .build()
    }
}
