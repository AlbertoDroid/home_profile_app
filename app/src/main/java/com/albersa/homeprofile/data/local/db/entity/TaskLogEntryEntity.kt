package com.albersa.homeprofile.data.local.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "task_log_entry",
    foreignKeys = [
        ForeignKey(
            entity = MaintenanceTaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("taskId")]
)
data class TaskLogEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskId: Int,
    val taskTitle: String,
    val taskCategory: String,
    val completedAt: Long,
    val notes: String? = null,
    val amended: Boolean = false
)
