package com.albersa.homeprofile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.albersa.homeprofile.ui.calendar.CalendarScreen
import com.albersa.homeprofile.ui.calendar.TaskDetailScreen
import com.albersa.homeprofile.ui.dashboard.DashboardScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingConstructionScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingGeneratingScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingHeatingScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingOutdoorScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingPropertyBasicsScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingReadyScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingSystemsScreen
import com.albersa.homeprofile.ui.onboarding.OnboardingWelcomeScreen
import com.albersa.homeprofile.ui.profile.ProfileScreen
import com.albersa.homeprofile.ui.tasklog.TaskLogScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.OnboardingWelcome.route) {
            OnboardingWelcomeScreen(
                onNext = { navController.navigate(Screen.OnboardingPropertyBasics.route) }
            )
        }
        composable(Screen.OnboardingPropertyBasics.route) {
            OnboardingPropertyBasicsScreen(
                onNext = { navController.navigate(Screen.OnboardingConstruction.route) }
            )
        }
        composable(Screen.OnboardingConstruction.route) {
            OnboardingConstructionScreen(
                onNext = { navController.navigate(Screen.OnboardingHeating.route) }
            )
        }
        composable(Screen.OnboardingHeating.route) {
            OnboardingHeatingScreen(
                onNext = { navController.navigate(Screen.OnboardingOutdoor.route) }
            )
        }
        composable(Screen.OnboardingOutdoor.route) {
            OnboardingOutdoorScreen(
                onNext = { navController.navigate(Screen.OnboardingSystems.route) }
            )
        }
        composable(Screen.OnboardingSystems.route) {
            OnboardingSystemsScreen(
                onNext = { navController.navigate(Screen.OnboardingGenerating.route) }
            )
        }
        composable(Screen.OnboardingGenerating.route) {
            OnboardingGeneratingScreen(
                onNext = { navController.navigate(Screen.OnboardingReady.route) }
            )
        }
        composable(Screen.OnboardingReady.route) {
            OnboardingReadyScreen(
                onNext = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.OnboardingWelcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.Calendar.route) { CalendarScreen() }
        composable(Screen.TaskLog.route) { TaskLogScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            TaskDetailScreen(taskId = backStackEntry.arguments!!.getInt("taskId"))
        }
    }
}
