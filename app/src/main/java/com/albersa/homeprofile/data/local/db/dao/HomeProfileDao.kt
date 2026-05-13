package com.albersa.homeprofile.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albersa.homeprofile.data.local.db.entity.HomeProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeProfileDao {

    @Query("SELECT * FROM home_profile WHERE id = 1")
    fun observe(): Flow<HomeProfileEntity?>

    @Query("SELECT * FROM home_profile WHERE id = 1")
    suspend fun get(): HomeProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: HomeProfileEntity)

    @Query("UPDATE home_profile SET profileComplete = :complete, updatedAt = :timestamp WHERE id = 1")
    suspend fun setProfileComplete(complete: Boolean, timestamp: Long)

    @Query("SELECT profileComplete FROM home_profile WHERE id = 1")
    suspend fun isProfileComplete(): Boolean?
}
