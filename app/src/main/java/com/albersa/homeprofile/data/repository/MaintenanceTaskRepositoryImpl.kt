package com.albersa.homeprofile.data.repository

import com.albersa.homeprofile.data.local.db.dao.MaintenanceTaskDao
import com.albersa.homeprofile.data.local.db.entity.MaintenanceTaskEntity
import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.model.MaintenanceTask
import com.albersa.homeprofile.domain.repository.MaintenanceTaskRepository
import com.albersa.homeprofile.domain.usecase.CalendarGenerationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MaintenanceTaskRepositoryImpl @Inject constructor(
    private val dao: MaintenanceTaskDao,
    private val generateCalendar: CalendarGenerationUseCase
) : MaintenanceTaskRepository {

    override suspend fun generateAndStore(profile: HomeProfile): Result<List<MaintenanceTask>> =
        generateCalendar(profile)

    override fun observeAll(): Flow<List<MaintenanceTask>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeForMonth(month: Int): Flow<List<MaintenanceTask>> =
        dao.observeForMonth(month).map { list -> list.map { it.toDomain() } }
}

private fun MaintenanceTaskEntity.toDomain() = MaintenanceTask(
    id = id,
    taskKey = taskKey,
    title = title,
    category = category,
    whyThisHome = whyThisHome,
    optimalMonthStart = optimalMonthStart,
    optimalMonthEnd = optimalMonthEnd,
    urgency = urgency,
    effortDiyHours = effortDiyHours,
    difficulty = difficulty,
    recurrence = recurrence,
    appliesBecause = appliesBecause,
    isCompletedThisCycle = isCompletedThisCycle
)
