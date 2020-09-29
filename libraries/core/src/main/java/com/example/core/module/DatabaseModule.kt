package com.example.core.module

import com.example.core.database.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(databaseRepository: DatabaseRepository): DatabaseRepository {
        return databaseRepository
    }
}