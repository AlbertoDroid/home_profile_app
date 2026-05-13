package com.albersa.homeprofile.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_profile")
data class HomeProfileEntity(
    @PrimaryKey val id: Int = 1,
    val propertyType: String? = null,
    val ownership: String? = null,
    val yearBuiltRange: String? = null,
    val bedrooms: Int? = null,
    val floors: Int? = null,
    val locationClimateZone: String? = null,
    val wallType: String? = null,
    val wallInsulation: String? = null,
    val roofType: String? = null,
    val loftInsulation: String? = null,
    val doubleGlazing: String? = null,
    val hasBasement: Boolean? = null,
    val hasConservatory: Boolean? = null,
    val heatingType: String? = null,
    val boilerType: String? = null,
    val boilerAgeRange: String? = null,
    val lastBoilerService: String? = null,
    val hasHotWaterTank: Boolean? = null,
    val hasRadiators: Boolean? = null,
    val underfloorHeatingZones: String? = null,
    val hasGarden: String? = null,
    val driveway: String? = null,
    val gutterType: String? = null,
    val externalWoodwork: String? = null,
    val boundaryType: String? = null,
    val hasFlatRoofSections: Boolean? = null,
    val hasSmokeAlarms: Boolean? = null,
    val stopValveKnown: Boolean? = null,
    val hasSepticTank: Boolean? = null,
    val hasSolar: Boolean = false,
    val hasEvCharger: Boolean? = null,
    val hasSecuritySystem: Boolean? = null,
    val profileComplete: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
