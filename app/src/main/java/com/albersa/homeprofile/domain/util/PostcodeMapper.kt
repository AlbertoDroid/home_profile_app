package com.albersa.homeprofile.domain.util

object PostcodeMapper {

    private val areaZones: Map<String, String> = buildMap {
        listOf("AB", "DD", "EH", "FK", "G", "HS", "IV", "KA", "KW", "KY", "ML", "PA", "PH", "TD", "ZE")
            .forEach { put(it, "scotland") }
        put("BT", "northern_ireland")
        listOf("CF", "LD", "LL", "NP", "SA")
            .forEach { put(it, "wales") }
        listOf(
            "BB", "BD", "BL", "CA", "CH", "CW", "DH", "DL", "DN", "FY",
            "HD", "HG", "HU", "HX", "L", "LA", "LS", "M", "NE", "OL",
            "PR", "S", "SK", "SR", "TS", "WA", "WF", "WN", "YO"
        ).forEach { put(it, "north_england") }
        listOf("B", "CV", "DE", "DY", "HR", "LE", "LN", "NG", "NN", "PE", "ST", "SY", "TF", "WR", "WS", "WV")
            .forEach { put(it, "midlands") }
        listOf(
            "AL", "BA", "BH", "BN", "BR", "BS", "CB", "CM", "CO", "CR",
            "CT", "DA", "DT", "E", "EC", "EN", "EX", "GL", "GU", "GY",
            "HA", "HP", "IG", "IM", "IP", "JE", "KT", "LU", "ME", "MK",
            "N", "NR", "NW", "OX", "PL", "PO", "RG", "RH", "RM", "SE",
            "SG", "SL", "SM", "SN", "SO", "SP", "SS", "SW", "TA", "TN",
            "TQ", "TR", "TW", "UB", "W", "WC", "WD"
        ).forEach { put(it, "south_england") }
    }

    fun mapToClimateZone(postcode: String): String {
        val letters = postcode.trim().uppercase().takeWhile { it.isLetter() }
        return areaZones[letters]
            ?: areaZones[letters.take(1)]
            ?: "unknown"
    }
}
