package com.albersa.homeprofile.data.local.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "maintenance_task",
    indices = [Index(value = ["taskKey"], unique = true)]
)
data class MaintenanceTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskKey: String,
    val title: String,
    val category: String,
    val whyThisHome: String,
    val optimalMonthStart: Int,
    val optimalMonthEnd: Int,
    val urgency: String,
    val effortDiyHours: Double? = null,
    val difficulty: String,
    val recurrence: String,
    val appliesBecause: String,
    val isCompletedThisCycle: Boolean = false,
    val snoozedUntil: Long? = null,
    val generatedAt: Long = 0L
)
