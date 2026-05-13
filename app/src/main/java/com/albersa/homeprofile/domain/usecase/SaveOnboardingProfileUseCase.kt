package com.albersa.homeprofile.domain.usecase

import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.repository.HomeProfileRepository
import javax.inject.Inject

class SaveOnboardingProfileUseCase @Inject constructor(
    private val repository: HomeProfileRepository
) {
    suspend operator fun invoke(profile: HomeProfile): Result<Unit> =
        repository.saveOnboardingData(profile.copy(profileComplete = true))
}
