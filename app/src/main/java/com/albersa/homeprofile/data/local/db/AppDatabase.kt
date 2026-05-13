package com.albersa.homeprofile.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.albersa.homeprofile.data.local.db.dao.HomeProfileDao
import com.albersa.homeprofile.data.local.db.dao.MaintenanceTaskDao
import com.albersa.homeprofile.data.local.db.dao.NotificationLogDao
import com.albersa.homeprofile.data.local.db.dao.TaskLogEntryDao
import com.albersa.homeprofile.data.local.db.entity.HomeProfileEntity
import com.albersa.homeprofile.data.local.db.entity.MaintenanceTaskEntity
import com.albersa.homeprofile.data.local.db.entity.NotificationLogEntity
import com.albersa.homeprofile.data.local.db.entity.TaskLogEntryEntity

@Database(
    entities = [
        HomeProfileEntity::class,
        MaintenanceTaskEntity::class,
        TaskLogEntryEntity::class,
        NotificationLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeProfileDao(): HomeProfileDao
    abstract fun maintenanceTaskDao(): MaintenanceTaskDao
    abstract fun taskLogEntryDao(): TaskLogEntryDao
    abstract fun notificationLogDao(): NotificationLogDao
}
