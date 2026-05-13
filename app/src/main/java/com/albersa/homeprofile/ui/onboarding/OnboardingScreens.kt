package com.albersa.homeprofile.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.albersa.homeprofile.ui.components.OnboardingScaffold
import com.albersa.homeprofile.ui.components.SelectionGroup
import com.albersa.homeprofile.ui.components.YesNoChipGroup

// ─── O1 — Welcome ────────────────────────────────────────────────────────────

@Composable
fun OnboardingWelcomeScreen(onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🏠",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Your home,\nlooked after",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(32.dp))
        BulletItem("Personalized to your property")
        Spacer(Modifier.height(12.dp))
        BulletItem("Timely seasonal reminders")
        Spacer(Modifier.height(12.dp))
        BulletItem("A full maintenance history")
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Takes about 4 minutes",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set Up My Home")
        }
    }
}

@Composable
private fun BulletItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("✓", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

// ─── O2 — Property Basics ────────────────────────────────────────────────────

@Composable
fun OnboardingPropertyBasicsScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    OnboardingScaffold(
        progressText = "Step 1 of 5",
        onBack = onBack,
        onSkip = onSkip,
        onCta = onContinue
    ) {
        ProfileSection("Property type") {
            SelectionGroup(
                options = OnboardingOptions.PROPERTY_TYPES,
                selectedOption = uiState.propertyType,
                onOptionSelected = viewModel::onPropertyTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Ownership") {
            SelectionGroup(
                options = OnboardingOptions.OWNERSHIP,
                selectedOption = uiState.ownership,
                onOptionSelected = viewModel::onOwnershipSelected
            )
        }
        SectionDivider()
        ProfileSection("Year built (approx.)") {
            SelectionGroup(
                options = OnboardingOptions.YEAR_BUILT,
                selectedOption = uiState.yearBuiltRange,
                onOptionSelected = viewModel::onYearBuiltSelected
            )
        }
        SectionDivider()
        ProfileSection("Bedrooms") {
            SelectionGroup(
                options = OnboardingOptions.BEDROOMS.map { OnboardingOptions.bedroomLabel(it) },
                selectedOption = uiState.bedrooms?.let { OnboardingOptions.bedroomLabel(it) },
                onOptionSelected = { label ->
                    val v = if (label == "5+") 5 else label.toIntOrNull() ?: return@SelectionGroup
                    viewModel.onBedroomsSelected(v)
                }
            )
        }
        SectionDivider()
        ProfileSection("Floors") {
            SelectionGroup(
                options = OnboardingOptions.FLOORS.map { OnboardingOptions.floorLabel(it) },
                selectedOption = uiState.floors?.let { OnboardingOptions.floorLabel(it) },
                onOptionSelected = { label ->
                    val v = if (label == "3+") 3 else label.toIntOrNull() ?: return@SelectionGroup
                    viewModel.onFloorsSelected(v)
                }
            )
        }
        SectionDivider()
        ProfileSection("ZIP Code (optional)") {
            OutlinedTextField(
                value = uiState.zipCodeInput,
                onValueChange = viewModel::onZipCodeChanged,
                placeholder = { Text("e.g. 90210") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = { Text("Used only to estimate your local climate — never stored") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ─── O3 — Construction ───────────────────────────────────────────────────────

@Composable
fun OnboardingConstructionScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    OnboardingScaffold(
        progressText = "Step 2 of 5",
        onBack = onBack,
        onSkip = onSkip,
        onCta = onContinue
    ) {
        ProfileSection("Wall / exterior type") {
            SelectionGroup(
                options = OnboardingOptions.WALL_TYPES,
                selectedOption = uiState.wallType,
                onOptionSelected = viewModel::onWallTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Wall insulation") {
            SelectionGroup(
                options = OnboardingOptions.WALL_INSULATION,
                selectedOption = uiState.wallInsulation,
                onOptionSelected = viewModel::onWallInsulationSelected
            )
        }
        SectionDivider()
        ProfileSection("Roof type") {
            SelectionGroup(
                options = OnboardingOptions.ROOF_TYPES,
                selectedOption = uiState.roofType,
                onOptionSelected = viewModel::onRoofTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Attic insulation") {
            SelectionGroup(
                options = OnboardingOptions.ATTIC_INSULATION,
                selectedOption = uiState.atticInsulation,
                onOptionSelected = viewModel::onAtticInsulationSelected
            )
        }
        SectionDivider()
        ProfileSection("Windows") {
            SelectionGroup(
                options = OnboardingOptions.WINDOW_TYPES,
                selectedOption = uiState.windowType,
                onOptionSelected = viewModel::onWindowTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Basement / crawl space") {
            SelectionGroup(
                options = OnboardingOptions.BASEMENT_TYPES,
                selectedOption = uiState.basementType,
                onOptionSelected = viewModel::onBasementTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Deck / patio") {
            YesNoChipGroup(value = uiState.hasDeck, onValueChange = viewModel::onHasDeckChanged)
        }
        SectionDivider()
        ProfileSection("Attached garage") {
            YesNoChipGroup(value = uiState.hasAttachedGarage, onValueChange = viewModel::onHasAttachedGarageChanged)
        }
    }
}

// ─── O4 — Heating & Cooling ──────────────────────────────────────────────────

@Composable
fun OnboardingHeatingScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    OnboardingScaffold(
        progressText = "Step 3 of 5",
        onBack = onBack,
        onSkip = onSkip,
        onCta = onContinue
    ) {
        ProfileSection("Heating type") {
            SelectionGroup(
                options = OnboardingOptions.HEATING_TYPES,
                selectedOption = uiState.heatingType,
                onOptionSelected = viewModel::onHeatingTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Cooling type") {
            SelectionGroup(
                options = OnboardingOptions.COOLING_TYPES,
                selectedOption = uiState.coolingType,
                onOptionSelected = viewModel::onCoolingTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("HVAC age (approx.)") {
            SelectionGroup(
                options = OnboardingOptions.HVAC_AGE,
                selectedOption = uiState.hvacAge,
                onOptionSelected = viewModel::onHvacAgeSelected
            )
        }
        SectionDivider()
        ProfileSection("Last HVAC service") {
            SelectionGroup(
                options = OnboardingOptions.LAST_HVAC_SERVICE,
                selectedOption = uiState.lastHvacService,
                onOptionSelected = viewModel::onLastHvacServiceSelected
            )
        }
        SectionDivider()
        ProfileSection("Water heater type") {
            SelectionGroup(
                options = OnboardingOptions.WATER_HEATER_TYPES,
                selectedOption = uiState.waterHeaterType,
                onOptionSelected = viewModel::onWaterHeaterTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Water heater age") {
            SelectionGroup(
                options = OnboardingOptions.WATER_HEATER_AGE,
                selectedOption = uiState.waterHeaterAge,
                onOptionSelected = viewModel::onWaterHeaterAgeSelected
            )
        }
        SectionDivider()
        ProfileSection("Humidifier / dehumidifier") {
            YesNoChipGroup(value = uiState.hasHumidifier, onValueChange = viewModel::onHasHumidifierChanged)
        }
    }
}

// ─── O5 — Outdoor & Exterior ─────────────────────────────────────────────────

@Composable
fun OnboardingOutdoorScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    OnboardingScaffold(
        progressText = "Step 4 of 5",
        onBack = onBack,
        onSkip = onSkip,
        onCta = onContinue
    ) {
        ProfileSection("Yard / lawn") {
            SelectionGroup(
                options = OnboardingOptions.YARD_TYPES,
                selectedOption = uiState.hasYard,
                onOptionSelected = viewModel::onYardTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Driveway") {
            YesNoChipGroup(value = uiState.driveway, onValueChange = viewModel::onDrivewayChanged)
        }
        SectionDivider()
        ProfileSection("Gutters & downspouts") {
            SelectionGroup(
                options = OnboardingOptions.GUTTER_TYPES,
                selectedOption = uiState.gutterType,
                onOptionSelected = viewModel::onGutterTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Siding type") {
            SelectionGroup(
                options = OnboardingOptions.SIDING_TYPES,
                selectedOption = uiState.sidingType,
                onOptionSelected = viewModel::onSidingTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Fence") {
            SelectionGroup(
                options = OnboardingOptions.FENCE_TYPES,
                selectedOption = uiState.fenceType,
                onOptionSelected = viewModel::onFenceTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Sprinkler / irrigation system") {
            YesNoChipGroup(value = uiState.hasSprinklerSystem, onValueChange = viewModel::onHasSprinklerSystemChanged)
        }
        SectionDivider()
        ProfileSection("Flat roof sections (garage, addition)") {
            YesNoChipGroup(value = uiState.hasFlatRoofSections, onValueChange = viewModel::onHasFlatRoofChanged)
        }
    }
}

// ─── O6 — Systems & Appliances ───────────────────────────────────────────────

@Composable
fun OnboardingSystemsScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    OnboardingScaffold(
        progressText = "Step 5 of 5",
        onBack = onBack,
        onSkip = onSkip,
        ctaLabel = "Finish",
        onCta = onContinue
    ) {
        ProfileSection("Smoke / CO alarms") {
            SelectionGroup(
                options = OnboardingOptions.SMOKE_ALARMS,
                selectedOption = uiState.smokeAlarmsSelection,
                onOptionSelected = viewModel::onSmokeAlarmsSelected
            )
        }
        SectionDivider()
        ProfileSection("Main water shut-off location known") {
            YesNoChipGroup(value = uiState.waterShutoffKnown, onValueChange = viewModel::onWaterShutoffKnownChanged)
        }
        SectionDivider()
        ProfileSection("Sump pump") {
            YesNoChipGroup(value = uiState.hasSumpPump, onValueChange = viewModel::onHasSumpPumpChanged)
        }
        SectionDivider()
        ProfileSection("Septic system") {
            YesNoChipGroup(value = uiState.hasSepticTank, onValueChange = viewModel::onHasSepticTankChanged)
        }
        SectionDivider()
        ProfileSection("Solar panels") {
            YesNoChipGroup(value = uiState.hasSolar, onValueChange = viewModel::onHasSolarChanged)
        }
        SectionDivider()
        ProfileSection("Electric vehicle charger") {
            YesNoChipGroup(value = uiState.hasEvCharger, onValueChange = viewModel::onHasEvChargerChanged)
        }
        SectionDivider()
        ProfileSection("Home security system") {
            YesNoChipGroup(value = uiState.hasSecuritySystem, onValueChange = viewModel::onHasSecuritySystemChanged)
        }
        if (uiState.saveError != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Couldn't save: ${uiState.saveError}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// ─── O7 — Generating Calendar (placeholder for M3) ───────────────────────────

@Composable
fun OnboardingGeneratingScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O7 — Generating Calendar")
    }
}

// ─── O8 — Your Home is Ready (placeholder for M3) ────────────────────────────

@Composable
fun OnboardingReadyScreen(onNext: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("O8 — Your Home is Ready")
    }
}

// ─── Shared helpers ──────────────────────────────────────────────────────────

@Composable
private fun ProfileSection(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        content()
    }
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
}
