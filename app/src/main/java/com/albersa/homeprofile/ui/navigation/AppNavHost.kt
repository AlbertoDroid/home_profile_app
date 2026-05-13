package com.albersa.homeprofile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.albersa.homeprofile.ui.onboarding.OnboardingViewModel
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
        composable(Screen.OnboardingWelcome.route) { welcomeEntry ->
            // ViewModel is created here, scoped to this back-stack entry.
            // O2–O6 retrieve it via getBackStackEntry so all screens share one instance.
            hiltViewModel<OnboardingViewModel>(welcomeEntry)
            OnboardingWelcomeScreen(
                onNext = { navController.navigate(Screen.OnboardingPropertyBasics.route) }
            )
        }

        composable(Screen.OnboardingPropertyBasics.route) {
            val welcomeEntry = remember(navController) {
                navController.getBackStackEntry(Screen.OnboardingWelcome.route)
            }
            val viewModel: OnboardingViewModel = hiltViewModel(welcomeEntry)
            OnboardingPropertyBasicsScreen(
                viewModel = viewModel,
                onContinue = { navController.navigate(Screen.OnboardingConstruction.route) },
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(Screen.OnboardingConstruction.route) }
            )
        }

        composable(Screen.OnboardingConstruction.route) {
            val welcomeEntry = remember(navController) {
                navController.getBackStackEntry(Screen.OnboardingWelcome.route)
            }
            val viewModel: OnboardingViewModel = hiltViewModel(welcomeEntry)
            OnboardingConstructionScreen(
                viewModel = viewModel,
                onContinue = { navController.navigate(Screen.OnboardingHeating.route) },
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(Screen.OnboardingHeating.route) }
            )
        }

        composable(Screen.OnboardingHeating.route) {
            val welcomeEntry = remember(navController) {
                navController.getBackStackEntry(Screen.OnboardingWelcome.route)
            }
            val viewModel: OnboardingViewModel = hiltViewModel(welcomeEntry)
            OnboardingHeatingScreen(
                viewModel = viewModel,
                onContinue = { navController.navigate(Screen.OnboardingOutdoor.route) },
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(Screen.OnboardingOutdoor.route) }
            )
        }

        composable(Screen.OnboardingOutdoor.route) {
            val welcomeEntry = remember(navController) {
                navController.getBackStackEntry(Screen.OnboardingWelcome.route)
            }
            val viewModel: OnboardingViewModel = hiltViewModel(welcomeEntry)
            OnboardingOutdoorScreen(
                viewModel = viewModel,
                onContinue = { navController.navigate(Screen.OnboardingSystems.route) },
                onBack = { navController.popBackStack() },
                onSkip = { navController.navigate(Screen.OnboardingSystems.route) }
            )
        }

        composable(Screen.OnboardingSystems.route) {
            val welcomeEntry = remember(navController) {
                navController.getBackStackEntry(Screen.OnboardingWelcome.route)
            }
            val viewModel: OnboardingViewModel = hiltViewModel(welcomeEntry)
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.saveSuccess) {
                if (uiState.saveSuccess) {
                    viewModel.onSaveSuccessConsumed()
                    navController.navigate(Screen.OnboardingGenerating.route)
                }
            }

            OnboardingSystemsScreen(
                viewModel = viewModel,
                onContinue = { viewModel.saveProfile() },
                onBack = { navController.popBackStack() },
                onSkip = { viewModel.saveProfile() }
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
