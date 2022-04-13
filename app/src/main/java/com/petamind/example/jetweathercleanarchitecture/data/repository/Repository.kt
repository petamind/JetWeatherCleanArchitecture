package com.petamind.example.jetweathercleanarchitecture.data.repository

import android.util.Log
import com.petamind.example.jetweathercleanarchitecture.data.DataOrException
import com.petamind.example.jetweathercleanarchitecture.data.WeatherAPI
import com.petamind.example.jetweathercleanarchitecture.data.model.Weather
import com.petamind.example.jetweathercleanarchitecture.util.Constants
import javax.inject.Inject


class Repository @Inject constructor(private val api: WeatherAPI) {
    suspend fun getWeather(cityId: String = Constants.City_ID["auckland"] ?:"6612109"): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeatherData(id = cityId)
        } catch (e: Exception
        ){
            Log.d(Constants.LOG_TAG, "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d(Constants.LOG_TAG, "getWeather: $response")
        return DataOrException(data= response)
    }
}