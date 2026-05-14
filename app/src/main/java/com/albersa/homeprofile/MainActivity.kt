package com.albersa.homeprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.albersa.homeprofile.data.local.datastore.AppPreferences
import com.albersa.homeprofile.ui.navigation.AppNavHost
import com.albersa.homeprofile.ui.navigation.Screen
import com.albersa.homeprofile.ui.theme.HomeProfileTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var prefs: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeProfileTheme {
                val onboardingDone by prefs.onboardingComplete.collectAsState(initial = null)
                when (onboardingDone) {
                    null -> Box(Modifier.fillMaxSize()) // wait for DataStore to resolve
                    true -> AppNavHost(
                        navController = rememberNavController(),
                        startDestination = Screen.Dashboard.route
                    )
                    false -> AppNavHost(
                        navController = rememberNavController(),
                        startDestination = Screen.OnboardingWelcome.route
                    )
                }
            }
        }
    }
}
