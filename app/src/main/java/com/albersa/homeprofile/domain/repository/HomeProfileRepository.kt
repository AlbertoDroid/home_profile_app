package com.albersa.homeprofile.domain.repository

import com.albersa.homeprofile.domain.model.HomeProfile

interface HomeProfileRepository {
    suspend fun saveOnboardingData(profile: HomeProfile): Result<Unit>
    suspend fun loadDraft(): Result<HomeProfile?>
}
