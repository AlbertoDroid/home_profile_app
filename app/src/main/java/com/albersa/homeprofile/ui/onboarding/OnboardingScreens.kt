package com.albersa.homeprofile.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun OnboardingWelcomeScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O1 — Welcome")
    }
}

@Composable
fun OnboardingPropertyBasicsScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O2 — Property Basics")
    }
}

@Composable
fun OnboardingConstructionScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O3 — Construction")
    }
}

@Composable
fun OnboardingHeatingScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O4 — Heating & Hot Water")
    }
}

@Composable
fun OnboardingOutdoorScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O5 — Outdoor & Garden")
    }
}

@Composable
fun OnboardingSystemsScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O6 — Systems & Appliances")
    }
}

@Composable
fun OnboardingGeneratingScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O7 — Generating Calendar")
    }
}

@Composable
fun OnboardingReadyScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O8 — Your Home is Ready")
    }
}
