package com.albersa.homeprofile.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.albersa.homeprofile.data.local.db.entity.NotificationLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationLogDao {

    @Query("SELECT * FROM notification_log ORDER BY sentAt DESC")
    fun observeAll(): Flow<List<NotificationLogEntity>>

    @Query("SELECT * FROM notification_log WHERE sentAt > :sinceTimestamp ORDER BY sentAt DESC")
    suspend fun getSince(sinceTimestamp: Long): List<NotificationLogEntity>

    @Insert
    suspend fun insert(log: NotificationLogEntity): Long

    @Query("SELECT MAX(sentAt) FROM notification_log")
    suspend fun getLastSentAt(): Long?
}
