package com.petamind.example.jetweathercleanarchitecture.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.petamind.example.jetweathercleanarchitecture.data.DataOrException
import com.petamind.example.jetweathercleanarchitecture.data.model.Weather
import com.petamind.example.jetweathercleanarchitecture.domain.viewmodel.WeatherViewModel
import com.petamind.example.jetweathercleanarchitecture.util.Constants
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

        ShowData(weatherViewModel = weatherViewModel, navController = navController)
    }
}

@Composable
fun ShowData(weatherViewModel: WeatherViewModel, navController: NavHostController) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    )
    {
        value = weatherViewModel.getWeather()
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavHostController) {
    Scaffold(topBar = {}) {
        MainContent(weather = weather, navController = navController)
    }
}

@Composable
fun MainContent(weather: Weather, navController: NavController) {
    AppTopAppBar(title = weather.city.name, icon = Icons.Default.ArrowBack,
        navController = navController, elevation = 5.dp
    ){
        Log.d(Constants.LOG_TAG, "MainContent: Button Clicked")
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
            .wrapContentSize(Alignment.CenterEnd)
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
            MenuView()
        }
    }, navigationIcon = {}, elevation = 16.dp)
    //
}

@Composable
private fun AppTopAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    TopAppBar(title = {
        Text(title)
    }, navigationIcon = {
        if (icon != null) {
            Icon(
                imageVector = icon, contentDescription = null,
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier.clickable {
                    onButtonClicked.invoke()
                }
            )
        }
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, "Search Icon")
            }
            Icon(imageVector = Icons.Default.MoreVert, "More Button")
        } else {
            Box {}
        }

    })
}