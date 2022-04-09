package com.petamind.example.jetweathercleanarchitecture.data.repository

import com.petamind.example.jetweathercleanarchitecture.data.DataOrException
import com.petamind.example.jetweathercleanarchitecture.data.WeatherAPI
import com.petamind.example.jetweathercleanarchitecture.data.model.WeatherObject
import com.petamind.example.jetweathercleanarchitecture.util.Constants
import java.lang.Exception
import javax.inject.Inject


class Repository @Inject constructor(private val api: WeatherAPI) {
    suspend fun getWeather(cityId: String = Constants.City_ID["auckland"] ?:"6612109"): DataOrException<WeatherObject, Boolean, Exception> {
        val response = try {
            api.getWeatherData(query = cityId)
        } catch (e: Exception
        ){
            return DataOrException(e = e)
        }
        return DataOrException(data= response)
    }
}