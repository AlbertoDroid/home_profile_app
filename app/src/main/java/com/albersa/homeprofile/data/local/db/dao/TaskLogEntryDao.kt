package com.albersa.homeprofile.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.albersa.homeprofile.data.local.db.entity.TaskLogEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskLogEntryDao {

    @Query("SELECT * FROM task_log_entry ORDER BY completedAt DESC")
    fun observeAll(): Flow<List<TaskLogEntryEntity>>

    @Query("SELECT * FROM task_log_entry WHERE taskId = :taskId ORDER BY completedAt DESC")
    fun observeForTask(taskId: Int): Flow<List<TaskLogEntryEntity>>

    @Query("SELECT * FROM task_log_entry WHERE completedAt BETWEEN :fromTimestamp AND :toTimestamp ORDER BY completedAt DESC")
    fun observeInRange(fromTimestamp: Long, toTimestamp: Long): Flow<List<TaskLogEntryEntity>>

    @Insert
    suspend fun insert(entry: TaskLogEntryEntity): Long

    @Update
    suspend fun update(entry: TaskLogEntryEntity)

    @Query("SELECT COUNT(*) FROM task_log_entry")
    suspend fun count(): Int
}
