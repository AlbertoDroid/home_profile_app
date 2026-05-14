package com.albersa.homeprofile.domain.repository

import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.model.MaintenanceTask
import kotlinx.coroutines.flow.Flow

interface MaintenanceTaskRepository {
    suspend fun generateAndStore(profile: HomeProfile): Result<List<MaintenanceTask>>
    fun observeAll(): Flow<List<MaintenanceTask>>
    fun observeForMonth(month: Int): Flow<List<MaintenanceTask>>
}
