package com.petamind.example.jetweathercleanarchitecture.di.module

import com.petamind.example.jetweathercleanarchitecture.data.WeatherAPI
import com.petamind.example.jetweathercleanarchitecture.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideOpenWeatherApi(): WeatherAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Weather_URL).build().create(WeatherAPI::class.java)
    }
}