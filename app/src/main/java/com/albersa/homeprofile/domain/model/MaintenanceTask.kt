package com.albersa.homeprofile.domain.model

data class MaintenanceTask(
    val id: Int,
    val taskKey: String,
    val title: String,
    val category: String,
    val whyThisHome: String,
    val optimalMonthStart: Int,
    val optimalMonthEnd: Int,
    val urgency: String,
    val effortDiyHours: Double?,
    val difficulty: String,
    val recurrence: String,
    val appliesBecause: String,
    val isCompletedThisCycle: Boolean = false
)
