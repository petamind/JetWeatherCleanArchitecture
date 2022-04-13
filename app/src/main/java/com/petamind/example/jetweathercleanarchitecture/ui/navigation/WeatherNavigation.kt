package com.petamind.example.jetweathercleanarchitecture.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.petamind.example.jetweathercleanarchitecture.domain.viewmodel.WeatherViewModel
import com.petamind.example.jetweathercleanarchitecture.ui.screens.AboutScreen
import com.petamind.example.jetweathercleanarchitecture.ui.screens.MainScreen
import com.petamind.example.jetweathercleanarchitecture.ui.screens.SearchScreen
import com.petamind.example.jetweathercleanarchitecture.ui.screens.SplashScreen

@Preview
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }

        val mainRoute = WeatherScreens.MainScreen.name
        composable("$mainRoute/{cityId}", arguments = listOf(
            navArgument(name = "cityId"){
                type = NavType.StringType
            }
        )){ navBack ->
            navBack.arguments?.getString("cityId").let {
                val weatherViewModel = hiltViewModel<WeatherViewModel>()
                MainScreen(navController = navController, weatherViewModel, city = it)
            }
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
    }
}