package com.example.core.module

import com.example.core.pref.AppPreferences
import com.example.core.pref.RxPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class SharedPrefsModule {
    @Provides
    @Singleton
    fun provideRxPreference(preferences: AppPreferences): RxPreferences {
        return preferences
    }
}