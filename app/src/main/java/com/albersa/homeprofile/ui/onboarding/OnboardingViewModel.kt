package com.albersa.homeprofile.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.usecase.SaveOnboardingProfileUseCase
import com.albersa.homeprofile.domain.util.PostcodeMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    // O2 — Property Basics
    val propertyType: String? = null,
    val ownership: String? = null,
    val yearBuiltRange: String? = null,
    val bedrooms: Int? = null,
    val floors: Int? = null,
    val postcodeInput: String = "",
    val locationClimateZone: String? = null,
    // O3 — Construction
    val wallType: String? = null,
    val wallInsulation: String? = null,
    val roofType: String? = null,
    val loftInsulation: String? = null,
    val doubleGlazing: String? = null,
    val hasBasement: Boolean? = null,
    val hasConservatory: Boolean? = null,
    // O4 — Heating & Hot Water
    val heatingType: String? = null,
    val boilerType: String? = null,
    val boilerAgeRange: String? = null,
    val lastBoilerService: String? = null,
    val hasHotWaterTank: Boolean? = null,
    val hasRadiators: Boolean? = null,
    val underfloorHeatingZones: String? = null,
    // O5 — Outdoor & Garden
    val hasGarden: String? = null,
    val driveway: Boolean? = null,
    val gutterType: String? = null,
    val externalWoodwork: String? = null,
    val boundaryType: String? = null,
    val hasFlatRoofSections: Boolean? = null,
    // O6 — Systems & Appliances
    val smokeAlarmsSelection: String? = null,
    val stopValveKnown: Boolean? = null,
    val hasSepticTank: Boolean? = null,
    val hasSolar: Boolean = false,
    val hasEvCharger: Boolean? = null,
    val hasSecuritySystem: Boolean? = null,
    // Meta
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingProfile: SaveOnboardingProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    // O2
    fun onPropertyTypeSelected(v: String) = _uiState.update { it.copy(propertyType = v) }
    fun onOwnershipSelected(v: String) = _uiState.update { it.copy(ownership = v) }
    fun onYearBuiltSelected(v: String) = _uiState.update { it.copy(yearBuiltRange = v) }
    fun onBedroomsSelected(v: Int) = _uiState.update { it.copy(bedrooms = v) }
    fun onFloorsSelected(v: Int) = _uiState.update { it.copy(floors = v) }
    fun onPostcodeChanged(v: String) = _uiState.update { it.copy(postcodeInput = v) }

    // O3
    fun onWallTypeSelected(v: String) = _uiState.update { it.copy(wallType = v) }
    fun onWallInsulationSelected(v: String) = _uiState.update { it.copy(wallInsulation = v) }
    fun onRoofTypeSelected(v: String) = _uiState.update { it.copy(roofType = v) }
    fun onLoftInsulationSelected(v: String) = _uiState.update { it.copy(loftInsulation = v) }
    fun onDoubleGlazingSelected(v: String) = _uiState.update { it.copy(doubleGlazing = v) }
    fun onHasBasementChanged(v: Boolean) = _uiState.update { it.copy(hasBasement = v) }
    fun onHasConservatoryChanged(v: Boolean) = _uiState.update { it.copy(hasConservatory = v) }

    // O4
    fun onHeatingTypeSelected(v: String) = _uiState.update {
        it.copy(
            heatingType = v,
            boilerType = if (v !in OnboardingOptions.BOILER_HEATING_TYPES) null else it.boilerType
        )
    }
    fun onBoilerTypeSelected(v: String) = _uiState.update { it.copy(boilerType = v) }
    fun onBoilerAgeSelected(v: String) = _uiState.update { it.copy(boilerAgeRange = v) }
    fun onLastBoilerServiceSelected(v: String) = _uiState.update { it.copy(lastBoilerService = v) }
    fun onHasHotWaterTankChanged(v: Boolean) = _uiState.update { it.copy(hasHotWaterTank = v) }
    fun onHasRadiatorsChanged(v: Boolean) = _uiState.update { it.copy(hasRadiators = v) }
    fun onUnderfloorZonesSelected(v: String) = _uiState.update { it.copy(underfloorHeatingZones = v) }

    // O5
    fun onGardenTypeSelected(v: String) = _uiState.update { it.copy(hasGarden = v) }
    fun onDrivewayChanged(v: Boolean) = _uiState.update { it.copy(driveway = v) }
    fun onGutterTypeSelected(v: String) = _uiState.update { it.copy(gutterType = v) }
    fun onExternalWoodworkSelected(v: String) = _uiState.update { it.copy(externalWoodwork = v) }
    fun onBoundaryTypeSelected(v: String) = _uiState.update { it.copy(boundaryType = v) }
    fun onHasFlatRoofChanged(v: Boolean) = _uiState.update { it.copy(hasFlatRoofSections = v) }

    // O6
    fun onSmokeAlarmsSelected(v: String) = _uiState.update { it.copy(smokeAlarmsSelection = v) }
    fun onStopValveKnownChanged(v: Boolean) = _uiState.update { it.copy(stopValveKnown = v) }
    fun onHasSepticTankChanged(v: Boolean) = _uiState.update { it.copy(hasSepticTank = v) }
    fun onHasSolarChanged(v: Boolean) = _uiState.update { it.copy(hasSolar = v) }
    fun onHasEvChargerChanged(v: Boolean) = _uiState.update { it.copy(hasEvCharger = v) }
    fun onHasSecuritySystemChanged(v: Boolean) = _uiState.update { it.copy(hasSecuritySystem = v) }

    fun onSaveSuccessConsumed() = _uiState.update { it.copy(saveSuccess = false) }

    fun saveProfile() {
        if (_uiState.value.isSaving) return
        val state = _uiState.value
        val climateZone = if (state.postcodeInput.isNotBlank())
            PostcodeMapper.mapToClimateZone(state.postcodeInput)
        else
            state.locationClimateZone

        val profile = HomeProfile(
            propertyType = state.propertyType,
            ownership = state.ownership,
            yearBuiltRange = state.yearBuiltRange,
            bedrooms = state.bedrooms,
            floors = state.floors,
            locationClimateZone = climateZone,
            wallType = state.wallType,
            wallInsulation = state.wallInsulation,
            roofType = state.roofType,
            loftInsulation = state.loftInsulation,
            doubleGlazing = state.doubleGlazing,
            hasBasement = state.hasBasement,
            hasConservatory = state.hasConservatory,
            heatingType = state.heatingType,
            boilerType = state.boilerType,
            boilerAgeRange = state.boilerAgeRange,
            lastBoilerService = state.lastBoilerService,
            hasHotWaterTank = state.hasHotWaterTank,
            hasRadiators = state.hasRadiators,
            underfloorHeatingZones = state.underfloorHeatingZones,
            hasGarden = state.hasGarden,
            driveway = state.driveway,
            gutterType = state.gutterType,
            externalWoodwork = state.externalWoodwork,
            boundaryType = state.boundaryType,
            hasFlatRoofSections = state.hasFlatRoofSections,
            hasSmokeAlarms = when (state.smokeAlarmsSelection) {
                "Yes" -> true
                "No" -> false
                else -> null
            },
            stopValveKnown = state.stopValveKnown,
            hasSepticTank = state.hasSepticTank,
            hasSolar = state.hasSolar,
            hasEvCharger = state.hasEvCharger,
            hasSecuritySystem = state.hasSecuritySystem
        )

        _uiState.update { it.copy(isSaving = true, saveError = null) }
        viewModelScope.launch {
            saveOnboardingProfile(profile).fold(
                onSuccess = {
                    _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isSaving = false, saveError = e.message ?: "Save failed") }
                }
            )
        }
    }
}
