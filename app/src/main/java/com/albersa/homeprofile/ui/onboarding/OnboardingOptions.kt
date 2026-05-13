package com.albersa.homeprofile.ui.onboarding

object OnboardingOptions {
    // O2 — Property Basics
    val PROPERTY_TYPES = listOf("Single-family detached", "Townhouse", "Condo / Apartment", "Duplex", "Mobile home")
    val OWNERSHIP = listOf("Own", "Renting")
    val YEAR_BUILT = listOf("Pre-1940", "1940–1959", "1960–1979", "1980–1999", "2000–2015", "2016+")
    val BEDROOMS = listOf(1, 2, 3, 4, 5)
    val FLOORS = listOf(1, 2, 3)

    // O3 — Construction
    val WALL_TYPES = listOf("Wood frame with siding", "Brick", "Stucco", "Concrete block", "Unknown")
    val WALL_INSULATION = listOf("Yes", "No", "Unknown")
    val ROOF_TYPES = listOf("Asphalt shingle", "Metal", "Tile", "Slate", "Flat / Low-slope")
    val ATTIC_INSULATION = listOf("Yes", "No", "Unknown")
    val WINDOW_TYPES = listOf("Double-pane (full)", "Double-pane (partial)", "Single-pane")
    val BASEMENT_TYPES = listOf("Full basement", "Crawl space", "Slab", "None")

    // O4 — Heating & Cooling
    val HEATING_TYPES = listOf(
        "Gas furnace", "Electric furnace", "Heat pump", "Boiler (radiant)", "Baseboard electric", "None"
    )
    val COOLING_TYPES = listOf("Central AC", "Heat pump", "Window units", "Evaporative cooler", "None")
    val HVAC_AGE = listOf("< 5 years", "5–10 years", "10–15 years", "15+ years", "Unknown")
    val LAST_HVAC_SERVICE = listOf("This year", "1–2 years ago", "3+ years ago", "Never", "Unknown")
    val WATER_HEATER_TYPES = listOf(
        "Tank (gas)", "Tank (electric)", "Tankless (gas)", "Tankless (electric)", "Heat pump water heater"
    )
    val WATER_HEATER_AGE = listOf("< 5 years", "5–10 years", "10+ years", "Unknown")

    // O5 — Outdoor & Exterior
    val YARD_TYPES = listOf("Front only", "Rear only", "Both", "None")
    val GUTTER_TYPES = listOf("Aluminum", "Vinyl", "Copper", "Steel", "None", "Unknown")
    val SIDING_TYPES = listOf("Vinyl", "Wood", "Fiber cement", "Brick", "Stucco", "Other")
    val FENCE_TYPES = listOf("Wood", "Metal", "Vinyl", "None")

    // O6 — Systems & Appliances
    val SMOKE_ALARMS = listOf("Yes", "No", "Unsure")

    fun bedroomLabel(n: Int) = if (n >= 5) "5+" else n.toString()
    fun floorLabel(n: Int) = if (n >= 3) "3+" else n.toString()
}
