package com.albersa.homeprofile.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_profile")
data class HomeProfileEntity(
    @PrimaryKey val id: Int = 1,
    // O2 — Property Basics
    val propertyType: String? = null,
    val ownership: String? = null,
    val yearBuiltRange: String? = null,
    val bedrooms: Int? = null,
    val floors: Int? = null,
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
    val driveway: String? = null,
    val gutterType: String? = null,
    val sidingType: String? = null,
    val fenceType: String? = null,
    val hasSprinklerSystem: Boolean? = null,
    val hasFlatRoofSections: Boolean? = null,
    // O6 — Systems & Appliances
    val hasSmokeAlarms: Boolean? = null,
    val waterShutoffKnown: Boolean? = null,
    val hasSumpPump: Boolean? = null,
    val hasSepticTank: Boolean? = null,
    val hasSolar: Boolean = false,
    val hasEvCharger: Boolean? = null,
    val hasSecuritySystem: Boolean? = null,
    val profileComplete: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
