package com.robertlevonyan.composable.newsapp.ui.screens.splash

import android.view.animation.AccelerateDecelerateInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.ui.navigation.NavigationScreens
import com.robertlevonyan.composable.newsapp.ui.theme.SplashIconSize
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val alpha = remember { Animatable(0.1f) }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    AccelerateDecelerateInterpolator().getInterpolation(it)
                }
            ),
        )
        delay(1000)
        navController.navigate(NavigationScreens.MainScreen.name) {
            launchSingleTop = true
            popUpTo(navController.graph.id) { inclusive = true }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_icon),
            modifier = Modifier.size(SplashIconSize),
            contentDescription = "Logo",
        )
    }
}
