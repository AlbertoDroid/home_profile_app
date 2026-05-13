package com.albersa.homeprofile.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_log")
data class NotificationLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val notificationType: String,
    val taskIdsIncluded: String,
    val sentAt: Long
)
