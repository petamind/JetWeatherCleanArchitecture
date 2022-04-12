package com.petamind.example.jetweathercleanarchitecture.util

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val Weather_URL = "https://api.openweathermap.org/"
    val City_ID = mapOf<String, String>("auckland" to "2193733", "london" to "2643743",
    "otago" to "6612109")
    const val LOG_TAG = "LOG_TAG"
}

fun formatDateTime(timeStamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDate(timeStamp: Int): String {
    val sdf = SimpleDateFormat("EEE")
    val date = Date(timeStamp.toLong() * 1000)
    return sdf.format(date)
}