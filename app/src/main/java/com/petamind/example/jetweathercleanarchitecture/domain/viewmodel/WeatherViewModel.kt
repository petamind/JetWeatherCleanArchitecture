package com.petamind.example.jetweathercleanarchitecture.domain.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petamind.example.jetweathercleanarchitecture.data.DataOrException
import com.petamind.example.jetweathercleanarchitecture.data.model.WeatherObject
import com.petamind.example.jetweathercleanarchitecture.data.repository.Repository
import com.petamind.example.jetweathercleanarchitecture.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        loadWeather()
    }

    private fun loadWeather() {
        getWeather("2193733")
    }

    private fun getWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather()
            data.value.loading = data.value.data.toString().isEmpty()
        }
        Log.d(Constants.LOG_TAG, "getWeather: ${data.value.data.toString()}")
    }
}