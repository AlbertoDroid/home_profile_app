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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
        BulletItem("Personalised to your property")
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
        ProfileSection("Postcode (optional)") {
            OutlinedTextField(
                value = uiState.postcodeInput,
                onValueChange = viewModel::onPostcodeChanged,
                placeholder = { Text("e.g. SW1A 1AA") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
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
        ProfileSection("Wall type") {
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
        ProfileSection("Loft insulation") {
            SelectionGroup(
                options = OnboardingOptions.LOFT_INSULATION,
                selectedOption = uiState.loftInsulation,
                onOptionSelected = viewModel::onLoftInsulationSelected
            )
        }
        SectionDivider()
        ProfileSection("Double glazing") {
            SelectionGroup(
                options = OnboardingOptions.DOUBLE_GLAZING,
                selectedOption = uiState.doubleGlazing,
                onOptionSelected = viewModel::onDoubleGlazingSelected
            )
        }
        SectionDivider()
        ProfileSection("Basement / cellar") {
            YesNoChipGroup(value = uiState.hasBasement, onValueChange = viewModel::onHasBasementChanged)
        }
        SectionDivider()
        ProfileSection("Conservatory / extension") {
            YesNoChipGroup(value = uiState.hasConservatory, onValueChange = viewModel::onHasConservatoryChanged)
        }
    }
}

// ─── O4 — Heating & Hot Water ────────────────────────────────────────────────

@Composable
fun OnboardingHeatingScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val showBoilerFields = uiState.heatingType in OnboardingOptions.BOILER_HEATING_TYPES
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
        if (showBoilerFields) {
            SectionDivider()
            ProfileSection("Boiler type") {
                SelectionGroup(
                    options = OnboardingOptions.BOILER_TYPES,
                    selectedOption = uiState.boilerType,
                    onOptionSelected = viewModel::onBoilerTypeSelected
                )
            }
            SectionDivider()
            ProfileSection("Boiler age (approx.)") {
                SelectionGroup(
                    options = OnboardingOptions.BOILER_AGE,
                    selectedOption = uiState.boilerAgeRange,
                    onOptionSelected = viewModel::onBoilerAgeSelected
                )
            }
            SectionDivider()
            ProfileSection("Last boiler service") {
                SelectionGroup(
                    options = OnboardingOptions.LAST_BOILER_SERVICE,
                    selectedOption = uiState.lastBoilerService,
                    onOptionSelected = viewModel::onLastBoilerServiceSelected
                )
            }
        }
        SectionDivider()
        ProfileSection("Hot water tank") {
            YesNoChipGroup(value = uiState.hasHotWaterTank, onValueChange = viewModel::onHasHotWaterTankChanged)
        }
        SectionDivider()
        ProfileSection("Radiators") {
            YesNoChipGroup(value = uiState.hasRadiators, onValueChange = viewModel::onHasRadiatorsChanged)
        }
        SectionDivider()
        ProfileSection("Underfloor heating") {
            SelectionGroup(
                options = OnboardingOptions.UNDERFLOOR_ZONES,
                selectedOption = uiState.underfloorHeatingZones,
                onOptionSelected = viewModel::onUnderfloorZonesSelected
            )
        }
    }
}

// ─── O5 — Outdoor & Garden ───────────────────────────────────────────────────

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
        ProfileSection("Garden") {
            SelectionGroup(
                options = OnboardingOptions.GARDEN_TYPES,
                selectedOption = uiState.hasGarden,
                onOptionSelected = viewModel::onGardenTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Driveway / parking") {
            YesNoChipGroup(value = uiState.driveway, onValueChange = viewModel::onDrivewayChanged)
        }
        SectionDivider()
        ProfileSection("Gutters & downpipes") {
            SelectionGroup(
                options = OnboardingOptions.GUTTER_TYPES,
                selectedOption = uiState.gutterType,
                onOptionSelected = viewModel::onGutterTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("External woodwork (fascias, soffits)") {
            SelectionGroup(
                options = OnboardingOptions.WOODWORK_TYPES,
                selectedOption = uiState.externalWoodwork,
                onOptionSelected = viewModel::onExternalWoodworkSelected
            )
        }
        SectionDivider()
        ProfileSection("Boundary") {
            SelectionGroup(
                options = OnboardingOptions.BOUNDARY_TYPES,
                selectedOption = uiState.boundaryType,
                onOptionSelected = viewModel::onBoundaryTypeSelected
            )
        }
        SectionDivider()
        ProfileSection("Flat roof sections (garage, bay window)") {
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
        ProfileSection("Mains stop valve location known") {
            YesNoChipGroup(value = uiState.stopValveKnown, onValueChange = viewModel::onStopValveKnownChanged)
        }
        SectionDivider()
        ProfileSection("Septic tank") {
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
