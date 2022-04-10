package com.petamind.example.jetweathercleanarchitecture.data.model

data class WeatherItem(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Float,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<WeatherObject>,
    val wind: Wind
)