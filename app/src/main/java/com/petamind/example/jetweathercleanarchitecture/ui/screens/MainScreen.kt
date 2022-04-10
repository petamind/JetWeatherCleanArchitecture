package com.petamind.example.jetweathercleanarchitecture.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.petamind.example.jetweathercleanarchitecture.R
import com.petamind.example.jetweathercleanarchitecture.data.DataOrException
import com.petamind.example.jetweathercleanarchitecture.data.model.Weather
import com.petamind.example.jetweathercleanarchitecture.domain.viewmodel.WeatherViewModel
import java.lang.Exception

@Composable
fun MainScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp), verticalArrangement = Arrangement.Top
    ) {
        SearchBar()
        ShowData(weatherViewModel = weatherViewModel)
    }
}

@Composable
fun ShowData(weatherViewModel: WeatherViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    )
    {
        value = weatherViewModel.getWeather()
    }.value

    if(weatherData.loading == true){
        CircularProgressIndicator()
    } else  if (weatherData.data != null){
        Text(text = "Main screen ${weatherData.data!!.city.country}")
    }
}

@Composable
fun MenuView() {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "ICON")

        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { /*TODO*/ }) {
                Text(text = "Favorite")
            }
            DropdownMenuItem(onClick = { /*TODO*/ }) {
                Text(text = "Favorite")
            }
            DropdownMenuItem(onClick = { /*TODO*/ }) {
                Text(text = "Favorite")
            }
        }
    }

}


@Composable
private fun SearchBar() {
    TopAppBar(title = {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(end = 70.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = "Moscow, RU")
        }
    }, navigationIcon = {}, elevation = 16.dp)
    //MenuView()
}

@Composable
private fun AppTopAppBar() {
    TopAppBar(title = { Text(text = "Weather App") }, navigationIcon = {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_cloud_24),
            contentDescription = "App Icon",
            modifier = Modifier.padding(start = 16.dp)
        )
    }, modifier = Modifier.fillMaxWidth(), elevation = 5.dp)
}