package com.albersa.homeprofile.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.model.MaintenanceTask
import com.albersa.homeprofile.domain.repository.MaintenanceTaskRepository
import com.albersa.homeprofile.domain.usecase.SaveOnboardingProfileUseCase
import com.albersa.homeprofile.domain.util.ZipCodeMapper
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
    val zipCodeInput: String = "",
    val locationClimateZone: String? = null,
    // O3 — Construction
    val wallType: String? = null,
    val wallInsulation: String? = null,
    val roofType: String? = null,
    val atticInsulation: String? = null,
    val windowType: String? = null,
    val basementType: String? = null,
    val hasDeck: Boolean? = null,
    val hasAttachedGarage: Boolean? = null,
    // O4 — Heating & Cooling
    val heatingType: String? = null,
    val coolingType: String? = null,
    val hvacAge: String? = null,
    val lastHvacService: String? = null,
    val waterHeaterType: String? = null,
    val waterHeaterAge: String? = null,
    val hasHumidifier: Boolean? = null,
    // O5 — Outdoor & Exterior
    val hasYard: String? = null,
    val driveway: Boolean? = null,
    val gutterType: String? = null,
    val sidingType: String? = null,
    val fenceType: String? = null,
    val hasSprinklerSystem: Boolean? = null,
    val hasFlatRoofSections: Boolean? = null,
    // O6 — Systems & Appliances
    val smokeAlarmsSelection: String? = null,
    val waterShutoffKnown: Boolean? = null,
    val hasSumpPump: Boolean? = null,
    val hasSepticTank: Boolean? = null,
    val hasSolar: Boolean = false,
    val hasEvCharger: Boolean? = null,
    val hasSecuritySystem: Boolean? = null,
    // Save state
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val saveSuccess: Boolean = false,
    // Generation state (O7–O8)
    val isGenerating: Boolean = false,
    val generationError: String? = null,
    val generationSuccess: Boolean = false,
    val generatedTaskCount: Int = 0,
    val previewTasks: List<MaintenanceTask> = emptyList()
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingProfile: SaveOnboardingProfileUseCase,
    private val taskRepository: MaintenanceTaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    // O2
    fun onPropertyTypeSelected(v: String) = _uiState.update { it.copy(propertyType = v) }
    fun onOwnershipSelected(v: String) = _uiState.update { it.copy(ownership = v) }
    fun onYearBuiltSelected(v: String) = _uiState.update { it.copy(yearBuiltRange = v) }
    fun onBedroomsSelected(v: Int) = _uiState.update { it.copy(bedrooms = v) }
    fun onFloorsSelected(v: Int) = _uiState.update { it.copy(floors = v) }
    fun onZipCodeChanged(v: String) = _uiState.update { it.copy(zipCodeInput = v) }

    // O3
    fun onWallTypeSelected(v: String) = _uiState.update { it.copy(wallType = v) }
    fun onWallInsulationSelected(v: String) = _uiState.update { it.copy(wallInsulation = v) }
    fun onRoofTypeSelected(v: String) = _uiState.update { it.copy(roofType = v) }
    fun onAtticInsulationSelected(v: String) = _uiState.update { it.copy(atticInsulation = v) }
    fun onWindowTypeSelected(v: String) = _uiState.update { it.copy(windowType = v) }
    fun onBasementTypeSelected(v: String) = _uiState.update { it.copy(basementType = v) }
    fun onHasDeckChanged(v: Boolean) = _uiState.update { it.copy(hasDeck = v) }
    fun onHasAttachedGarageChanged(v: Boolean) = _uiState.update { it.copy(hasAttachedGarage = v) }

    // O4
    fun onHeatingTypeSelected(v: String) = _uiState.update { it.copy(heatingType = v) }
    fun onCoolingTypeSelected(v: String) = _uiState.update { it.copy(coolingType = v) }
    fun onHvacAgeSelected(v: String) = _uiState.update { it.copy(hvacAge = v) }
    fun onLastHvacServiceSelected(v: String) = _uiState.update { it.copy(lastHvacService = v) }
    fun onWaterHeaterTypeSelected(v: String) = _uiState.update { it.copy(waterHeaterType = v) }
    fun onWaterHeaterAgeSelected(v: String) = _uiState.update { it.copy(waterHeaterAge = v) }
    fun onHasHumidifierChanged(v: Boolean) = _uiState.update { it.copy(hasHumidifier = v) }

    // O5
    fun onYardTypeSelected(v: String) = _uiState.update { it.copy(hasYard = v) }
    fun onDrivewayChanged(v: Boolean) = _uiState.update { it.copy(driveway = v) }
    fun onGutterTypeSelected(v: String) = _uiState.update { it.copy(gutterType = v) }
    fun onSidingTypeSelected(v: String) = _uiState.update { it.copy(sidingType = v) }
    fun onFenceTypeSelected(v: String) = _uiState.update { it.copy(fenceType = v) }
    fun onHasSprinklerSystemChanged(v: Boolean) = _uiState.update { it.copy(hasSprinklerSystem = v) }
    fun onHasFlatRoofChanged(v: Boolean) = _uiState.update { it.copy(hasFlatRoofSections = v) }

    // O6
    fun onSmokeAlarmsSelected(v: String) = _uiState.update { it.copy(smokeAlarmsSelection = v) }
    fun onWaterShutoffKnownChanged(v: Boolean) = _uiState.update { it.copy(waterShutoffKnown = v) }
    fun onHasSumpPumpChanged(v: Boolean) = _uiState.update { it.copy(hasSumpPump = v) }
    fun onHasSepticTankChanged(v: Boolean) = _uiState.update { it.copy(hasSepticTank = v) }
    fun onHasSolarChanged(v: Boolean) = _uiState.update { it.copy(hasSolar = v) }
    fun onHasEvChargerChanged(v: Boolean) = _uiState.update { it.copy(hasEvCharger = v) }
    fun onHasSecuritySystemChanged(v: Boolean) = _uiState.update { it.copy(hasSecuritySystem = v) }

    fun onSaveSuccessConsumed() = _uiState.update { it.copy(saveSuccess = false) }
    fun onGenerationSuccessConsumed() = _uiState.update { it.copy(generationSuccess = false) }

    fun saveProfile() {
        if (_uiState.value.isSaving) return
        val profile = buildProfile(_uiState.value)
        _uiState.update { it.copy(isSaving = true, saveError = null) }
        viewModelScope.launch {
            saveOnboardingProfile(profile).fold(
                onSuccess = {
                    _uiState.update { it.copy(isSaving = false, saveSuccess = true, isGenerating = true) }
                    generateCalendar(profile)
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isSaving = false, saveError = e.message ?: "Save failed") }
                }
            )
        }
    }

    fun retryGeneration() {
        val state = _uiState.value
        if (state.isGenerating) return
        _uiState.update { it.copy(isGenerating = true, generationError = null) }
        viewModelScope.launch {
            generateCalendar(buildProfile(state))
        }
    }

    private suspend fun generateCalendar(profile: HomeProfile) {
        taskRepository.generateAndStore(profile).fold(
            onSuccess = { tasks ->
                val preview = tasks
                    .sortedBy { it.optimalMonthStart }
                    .take(3)
                _uiState.update {
                    it.copy(
                        isGenerating = false,
                        generationSuccess = true,
                        generatedTaskCount = tasks.size,
                        previewTasks = preview
                    )
                }
            },
            onFailure = { e ->
                _uiState.update {
                    it.copy(
                        isGenerating = false,
                        generationError = e.message ?: "Calendar generation failed"
                    )
                }
            }
        )
    }

    private fun buildProfile(state: OnboardingUiState): HomeProfile {
        val climateZone = if (state.zipCodeInput.isNotBlank())
            ZipCodeMapper.mapToClimateZone(state.zipCodeInput)
        else
            state.locationClimateZone

        return HomeProfile(
            propertyType = state.propertyType,
            ownership = state.ownership,
            yearBuiltRange = state.yearBuiltRange,
            bedrooms = state.bedrooms,
            floors = state.floors,
            locationClimateZone = climateZone,
            wallType = state.wallType,
            wallInsulation = state.wallInsulation,
            roofType = state.roofType,
            atticInsulation = state.atticInsulation,
            windowType = state.windowType,
            basementType = state.basementType,
            hasDeck = state.hasDeck,
            hasAttachedGarage = state.hasAttachedGarage,
            heatingType = state.heatingType,
            coolingType = state.coolingType,
            hvacAge = state.hvacAge,
            lastHvacService = state.lastHvacService,
            waterHeaterType = state.waterHeaterType,
            waterHeaterAge = state.waterHeaterAge,
            hasHumidifier = state.hasHumidifier,
            hasYard = state.hasYard,
            driveway = state.driveway,
            gutterType = state.gutterType,
            sidingType = state.sidingType,
            fenceType = state.fenceType,
            hasSprinklerSystem = state.hasSprinklerSystem,
            hasFlatRoofSections = state.hasFlatRoofSections,
            hasSmokeAlarms = when (state.smokeAlarmsSelection) {
                "Yes" -> true
                "No" -> false
                else -> null
            },
            waterShutoffKnown = state.waterShutoffKnown,
            hasSumpPump = state.hasSumpPump,
            hasSepticTank = state.hasSepticTank,
            hasSolar = state.hasSolar,
            hasEvCharger = state.hasEvCharger,
            hasSecuritySystem = state.hasSecuritySystem
        )
    }
}
