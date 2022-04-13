package com.petamind.example.jetweathercleanarchitecture.ui.screens


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.petamind.example.jetweathercleanarchitecture.R
import com.petamind.example.jetweathercleanarchitecture.ui.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale =  remember {
        Animatable(initialValue = 0f)
    }

    val defaultCityId = "2193733"

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.99f, animationSpec = tween(
            durationMillis = 800,
            easing = {
                OvershootInterpolator(8f)
                    .getInterpolation(it)
            }
        ))
        delay(2000L)
        navController.navigate(route = WeatherScreens.MainScreen.name + "/$defaultCityId")
    })
    Surface(
        modifier = Modifier
            .size(300.dp)
            .scale(scale.value), shape = CircleShape, color = Color.White,
        border = BorderStroke(width = 4.dp, color = Color.LightGray), elevation = 5.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_cloud_24),
                contentDescription = "weather icon",
                Modifier.size(150.dp)
            )
            Text(text = "Find the Sun?", style = MaterialTheme.typography.h4)
        }
    }

}