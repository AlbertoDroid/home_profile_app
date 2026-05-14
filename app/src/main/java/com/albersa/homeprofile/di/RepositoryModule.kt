package com.albersa.homeprofile.di

import com.albersa.homeprofile.data.repository.HomeProfileRepositoryImpl
import com.albersa.homeprofile.data.repository.MaintenanceTaskRepositoryImpl
import com.albersa.homeprofile.domain.repository.HomeProfileRepository
import com.albersa.homeprofile.domain.repository.MaintenanceTaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeProfileRepository(
        impl: HomeProfileRepositoryImpl
    ): HomeProfileRepository

    @Binds
    @Singleton
    abstract fun bindMaintenanceTaskRepository(
        impl: MaintenanceTaskRepositoryImpl
    ): MaintenanceTaskRepository
}
