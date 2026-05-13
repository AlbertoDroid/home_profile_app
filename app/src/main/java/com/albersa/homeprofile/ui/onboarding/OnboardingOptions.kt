package com.albersa.homeprofile.ui.onboarding

object OnboardingOptions {
    val PROPERTY_TYPES = listOf("Detached", "Semi-detached", "Terraced", "Flat / Apartment", "Bungalow")
    val OWNERSHIP = listOf("Own (freehold)", "Own (leasehold)", "Renting")
    val YEAR_BUILT = listOf("Pre-1919", "1919–1944", "1945–1979", "1980–1999", "2000–2015", "2016+")
    val BEDROOMS = listOf(1, 2, 3, 4, 5)
    val FLOORS = listOf(1, 2, 3)

    val WALL_TYPES = listOf("Cavity wall", "Solid wall", "Timber frame", "Unknown")
    val WALL_INSULATION = listOf("Yes", "No", "Unknown")
    val ROOF_TYPES = listOf("Pitched (tiles)", "Pitched (slate)", "Flat", "Mixed")
    val LOFT_INSULATION = listOf("Yes", "No", "No loft")
    val DOUBLE_GLAZING = listOf("Full", "Partial", "None")

    val HEATING_TYPES = listOf(
        "Gas central heating", "Oil boiler", "Heat pump",
        "Electric storage heaters", "Underfloor heating", "None"
    )
    val BOILER_HEATING_TYPES = setOf("Gas central heating", "Oil boiler")
    val BOILER_TYPES = listOf("Combi", "System", "Regular (conventional)")
    val BOILER_AGE = listOf("< 5 years", "5–10 years", "10–15 years", "15+ years", "Unknown")
    val LAST_BOILER_SERVICE = listOf("This year", "1–2 years ago", "3+ years ago", "Never", "Unknown")
    val UNDERFLOOR_ZONES = listOf("None", "Ground floor", "Whole house")

    val GARDEN_TYPES = listOf("Front only", "Rear only", "Both", "None")
    val GUTTER_TYPES = listOf("Plastic", "Cast iron", "Unknown")
    val WOODWORK_TYPES = listOf("Wood", "UPVC", "Unknown")
    val BOUNDARY_TYPES = listOf("Fence", "Wall", "Hedge", "Mixed", "None")

    val SMOKE_ALARMS = listOf("Yes", "No", "Unsure")

    fun bedroomLabel(n: Int) = if (n >= 5) "5+" else n.toString()
    fun floorLabel(n: Int) = if (n >= 3) "3+" else n.toString()
}
