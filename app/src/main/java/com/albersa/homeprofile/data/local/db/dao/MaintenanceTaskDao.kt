package com.albersa.homeprofile.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.albersa.homeprofile.data.local.db.entity.MaintenanceTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceTaskDao {

    @Query("SELECT * FROM maintenance_task ORDER BY optimalMonthStart ASC")
    fun observeAll(): Flow<List<MaintenanceTaskEntity>>

    @Query("SELECT * FROM maintenance_task WHERE id = :id")
    suspend fun getById(id: Int): MaintenanceTaskEntity?

    @Query("SELECT * FROM maintenance_task WHERE category = :category ORDER BY optimalMonthStart ASC")
    fun observeByCategory(category: String): Flow<List<MaintenanceTaskEntity>>

    @Query("SELECT * FROM maintenance_task WHERE optimalMonthStart <= :month AND optimalMonthEnd >= :month ORDER BY urgency DESC")
    fun observeForMonth(month: Int): Flow<List<MaintenanceTaskEntity>>

    @Query("SELECT * FROM maintenance_task WHERE isCompletedThisCycle = 0 ORDER BY optimalMonthStart ASC")
    fun observeIncomplete(): Flow<List<MaintenanceTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<MaintenanceTaskEntity>)

    @Update
    suspend fun update(task: MaintenanceTaskEntity)

    @Query("UPDATE maintenance_task SET isCompletedThisCycle = :completed WHERE id = :id")
    suspend fun setCompleted(id: Int, completed: Boolean)

    @Query("UPDATE maintenance_task SET snoozedUntil = :until WHERE id = :id")
    suspend fun snooze(id: Int, until: Long?)

    @Query("UPDATE maintenance_task SET isCompletedThisCycle = 0, snoozedUntil = NULL")
    suspend fun resetAllForNewCycle()

    @Query("DELETE FROM maintenance_task")
    suspend fun deleteAll()
}
