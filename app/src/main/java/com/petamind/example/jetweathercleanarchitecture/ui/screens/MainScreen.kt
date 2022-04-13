package com.petamind.example.jetweathercleanarchitecture.ui.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.petamind.example.jetweathercleanarchitecture.data.DataOrException
import com.petamind.example.jetweathercleanarchitecture.data.model.Weather
import com.petamind.example.jetweathercleanarchitecture.domain.viewmodel.WeatherViewModel
import com.petamind.example.jetweathercleanarchitecture.ui.navigation.WeatherScreens
import com.petamind.example.jetweathercleanarchitecture.util.Constants
import com.petamind.example.jetweathercleanarchitecture.util.formatDate
import com.petamind.example.jetweathercleanarchitecture.util.formatDateTime
import java.lang.Exception

@Composable
fun MainScreen(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    city: String?
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp), verticalArrangement = Arrangement.Top
    ) {
        ShowData(weatherViewModel = weatherViewModel, navController = navController, city = city)
    }
}

@Composable
fun ShowData(weatherViewModel: WeatherViewModel, navController: NavHostController, city: String?) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    )
    {
        value = weatherViewModel.getWeather(cityId = city?:"2193733")
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
        Column {
            AppTopAppBar(
                title = weather.city.name, icon = Icons.Default.ArrowBack,
                elevation = 5.dp, navController = navController, onButtonClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                    Log.d(Constants.LOG_TAG, "MainScaffold: Button Clicked")
                }
            ) {
                navController.popBackStack()
            }
            val imageUrl =
                "https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}.png"
            MainContent(weather = weather, navController = navController, imageUrl = imageUrl)
        }

    }
}

@Composable
fun MainContent(weather: Weather, navController: NavController, imageUrl: String) {
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDateTime(weather.list[0].dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
            color = MaterialTheme.colors.secondaryVariant
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateIcon(imageUrl = imageUrl)
                Text(
                    text = "${weather.list[0].main.temp}º",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weather.list[0].weather[0].description, fontStyle = FontStyle.Italic)

            }
        }
        WeatherDetails(weather = weather)
    }
}

@Composable
fun WeatherDetails(weather: Weather) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(weather.list.groupBy { formatDate(it.dt) }.toList().map {
            it.second[0]
        }) { weatherItem ->
            Surface(shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = Color.Yellow, modifier = Modifier.padding(3.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = formatDate(weatherItem.dt) + " : "+weatherItem.weather[0].description)
                }

            }


        }
    }

}

@Composable
fun WeatherStateIcon(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "",
        modifier = Modifier.size(64.dp)
    )
}

@Composable
fun MenuView() {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "ICON")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd).absolutePadding(top = 20.dp, right = 20.dp)
    ) {

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { /*TODO*/ }) {
               // Row(Modifier.fillMaxWidth().wrapContentSize(align = TopEnd)) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favicon")
                    Text(text = "Favorite")
                //}
            }
            DropdownMenuItem(onClick = { /*TODO*/ }) {
                //Row(Modifier.fillMaxWidth().wrapContentSize(align = TopEnd)) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Favicon")
                    Text(text = "About")
                //}
            }
            DropdownMenuItem(onClick = { /*TODO*/ }) {
                //Row(Modifier.fillMaxWidth().wrapContentSize(align = TopEnd)) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Favicon")
                    Text(text = "Settings")
                //}
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
fun AppTopAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    onBackButtonClicked: () -> Boolean
) {
    TopAppBar(title = {
        Text(
            title,
            Modifier
                .fillMaxWidth()
                .then(Modifier.wrapContentWidth(align = CenterHorizontally))
        )
    }, navigationIcon = {
        if (icon != null) {
            Icon(
                imageVector = icon, contentDescription = null,
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier.clickable {
                    onBackButtonClicked.invoke()
                }
            )
        }
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = { onButtonClicked.invoke() }) {
                Icon(imageVector = Icons.Default.Search, "Search Icon")
            }
            //Icon(imageVector = Icons.Default.MoreVert, "More Button")
            MenuView()
        } else {
            Box {}
        }
    })
}