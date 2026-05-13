package com.albersa.homeprofile.domain.util

object ZipCodeMapper {
    fun mapToClimateZone(zipCode: String): String {
        val prefix = zipCode.trim().take(3).toIntOrNull() ?: return "unknown"
        return when (prefix / 100) {
            0, 1 -> "northeast"       // CT, MA, ME, NH, VT, RI, NY, NJ, PA, DE, MD
            2, 3 -> "southeast"       // VA, WV, NC, SC, GA, FL, AL, MS, AR, LA, TN
            4, 5 -> "midwest"         // OH, IN, MI, IL, WI, MN, IA, MO
            6 -> "great_plains"       // ND, SD, NE, KS
            7 -> "south_central"      // TX, OK, LA
            8 -> "mountain"           // CO, NM, AZ, UT, NV, ID, WY, MT
            9 -> "west_coast"         // CA, OR, WA, AK, HI
            else -> "unknown"
        }
    }
}
