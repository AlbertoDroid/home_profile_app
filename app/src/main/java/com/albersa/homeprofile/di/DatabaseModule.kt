package com.albersa.homeprofile.di

import android.content.Context
import androidx.room.Room
import com.albersa.homeprofile.data.local.db.AppDatabase
import com.albersa.homeprofile.data.local.db.dao.HomeProfileDao
import com.albersa.homeprofile.data.local.db.dao.MaintenanceTaskDao
import com.albersa.homeprofile.data.local.db.dao.NotificationLogDao
import com.albersa.homeprofile.data.local.db.dao.TaskLogEntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "home_profile.db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideHomeProfileDao(db: AppDatabase): HomeProfileDao = db.homeProfileDao()

    @Provides
    fun provideMaintenanceTaskDao(db: AppDatabase): MaintenanceTaskDao = db.maintenanceTaskDao()

    @Provides
    fun provideTaskLogEntryDao(db: AppDatabase): TaskLogEntryDao = db.taskLogEntryDao()

    @Provides
    fun provideNotificationLogDao(db: AppDatabase): NotificationLogDao = db.notificationLogDao()
}
