package com.example.circularplayground.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.circularplayground.ui.detail.DetailScreen
import com.google.accompanist.navigation.animation.composable
import com.example.circularplayground.ui.main.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

sealed class Screens(private val route: String) {
    fun createRoute() = this.route
    object MainScreen: Screens(route = "main")
    object DetailScreen: Screens(route = "detail?ref={clientRef}") {
        fun createRoute(clientRef: String) = "detail?ref=$clientRef"
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.MainScreen.createRoute(),
    ) {

        composable(
            route = Screens.MainScreen.createRoute()
        ) {
            MainScreen(
                openDetails = {
                    navController.navigate(Screens.DetailScreen.createRoute(clientRef = it))
                },
            )
        }

        composable(
            route = Screens.DetailScreen.createRoute()
        ) {
            DetailScreen()
        }
    }
}
