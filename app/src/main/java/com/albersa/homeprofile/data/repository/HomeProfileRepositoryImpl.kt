package com.albersa.homeprofile.data.repository

import com.albersa.homeprofile.data.local.datastore.AppPreferences
import com.albersa.homeprofile.data.local.db.dao.HomeProfileDao
import com.albersa.homeprofile.data.local.db.entity.HomeProfileEntity
import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.repository.HomeProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeProfileRepositoryImpl @Inject constructor(
    private val dao: HomeProfileDao,
    private val prefs: AppPreferences
) : HomeProfileRepository {

    override suspend fun saveOnboardingData(profile: HomeProfile): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val now = System.currentTimeMillis()
                val existing = dao.get()
                dao.upsert(profile.toEntity(createdAt = existing?.createdAt ?: now, updatedAt = now))
                prefs.setOnboardingComplete(true)
            }
        }

    override suspend fun loadDraft(): Result<HomeProfile?> =
        withContext(Dispatchers.IO) {
            runCatching { dao.get()?.toDomain() }
        }
}

private fun HomeProfile.toEntity(createdAt: Long, updatedAt: Long) = HomeProfileEntity(
    propertyType = propertyType,
    ownership = ownership,
    yearBuiltRange = yearBuiltRange,
    bedrooms = bedrooms,
    floors = floors,
    locationClimateZone = locationClimateZone,
    wallType = wallType,
    wallInsulation = wallInsulation,
    roofType = roofType,
    loftInsulation = loftInsulation,
    doubleGlazing = doubleGlazing,
    hasBasement = hasBasement,
    hasConservatory = hasConservatory,
    heatingType = heatingType,
    boilerType = boilerType,
    boilerAgeRange = boilerAgeRange,
    lastBoilerService = lastBoilerService,
    hasHotWaterTank = hasHotWaterTank,
    hasRadiators = hasRadiators,
    underfloorHeatingZones = underfloorHeatingZones,
    hasGarden = hasGarden,
    driveway = when (driveway) { true -> "yes"; false -> "no"; else -> null },
    gutterType = gutterType,
    externalWoodwork = externalWoodwork,
    boundaryType = boundaryType,
    hasFlatRoofSections = hasFlatRoofSections,
    hasSmokeAlarms = hasSmokeAlarms,
    stopValveKnown = stopValveKnown,
    hasSepticTank = hasSepticTank,
    hasSolar = hasSolar,
    hasEvCharger = hasEvCharger,
    hasSecuritySystem = hasSecuritySystem,
    profileComplete = profileComplete,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun HomeProfileEntity.toDomain() = HomeProfile(
    propertyType = propertyType,
    ownership = ownership,
    yearBuiltRange = yearBuiltRange,
    bedrooms = bedrooms,
    floors = floors,
    locationClimateZone = locationClimateZone,
    wallType = wallType,
    wallInsulation = wallInsulation,
    roofType = roofType,
    loftInsulation = loftInsulation,
    doubleGlazing = doubleGlazing,
    hasBasement = hasBasement,
    hasConservatory = hasConservatory,
    heatingType = heatingType,
    boilerType = boilerType,
    boilerAgeRange = boilerAgeRange,
    lastBoilerService = lastBoilerService,
    hasHotWaterTank = hasHotWaterTank,
    hasRadiators = hasRadiators,
    underfloorHeatingZones = underfloorHeatingZones,
    hasGarden = hasGarden,
    driveway = when (driveway) { "yes" -> true; "no" -> false; else -> null },
    gutterType = gutterType,
    externalWoodwork = externalWoodwork,
    boundaryType = boundaryType,
    hasFlatRoofSections = hasFlatRoofSections,
    hasSmokeAlarms = hasSmokeAlarms,
    stopValveKnown = stopValveKnown,
    hasSepticTank = hasSepticTank,
    hasSolar = hasSolar,
    hasEvCharger = hasEvCharger,
    hasSecuritySystem = hasSecuritySystem,
    profileComplete = profileComplete
)
