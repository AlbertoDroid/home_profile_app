package com.albersa.homeprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.albersa.homeprofile.ui.navigation.AppNavHost
import com.albersa.homeprofile.ui.navigation.Screen
import com.albersa.homeprofile.ui.theme.HomeProfileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeProfileTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    startDestination = Screen.OnboardingWelcome.route
                )
            }
        }
    }
}
