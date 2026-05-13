package com.albersa.homeprofile.ui.navigation

sealed class Screen(val route: String) {
    data object OnboardingWelcome : Screen("onboarding/welcome")
    data object OnboardingPropertyBasics : Screen("onboarding/property_basics")
    data object OnboardingConstruction : Screen("onboarding/construction")
    data object OnboardingHeating : Screen("onboarding/heating")
    data object OnboardingOutdoor : Screen("onboarding/outdoor")
    data object OnboardingSystems : Screen("onboarding/systems")
    data object OnboardingGenerating : Screen("onboarding/generating")
    data object OnboardingReady : Screen("onboarding/ready")

    data object Dashboard : Screen("dashboard")
    data object Calendar : Screen("calendar")
    data object TaskLog : Screen("task_log")
    data object Profile : Screen("profile")

    data object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int) = "task_detail/$taskId"
    }
}
