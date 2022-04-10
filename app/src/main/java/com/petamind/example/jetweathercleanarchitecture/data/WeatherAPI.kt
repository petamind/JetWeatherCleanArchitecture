package com.petamind.example.jetweathercleanarchitecture.data

import com.petamind.example.jetweathercleanarchitecture.BuildConfig
import com.petamind.example.jetweathercleanarchitecture.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET(value = "data/2.5/forecast")
    suspend fun getWeatherData(
        @Query(value = "") query: String,
        @Query(value = "id") id: String = "2193733",
        @Query(value = "appid") appid: String = BuildConfig.WEATHER_KEY,
        @Query(value = "units") units: String = "metric"
    ) : Weather

}