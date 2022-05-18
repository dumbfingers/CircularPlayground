package com.example.circularplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import com.example.circularplayground.theme.CircularPlaygroundTheme
import com.example.circularplayground.ui.AppNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberAnimatedNavController()
            CircularPlaygroundTheme {
                Scaffold {
                    AppNavigation(navController = navHostController)
                }
            }
        }
    }
}
