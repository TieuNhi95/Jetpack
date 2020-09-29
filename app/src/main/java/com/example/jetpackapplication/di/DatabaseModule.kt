package com.example.jetpackapplication.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DatabaseModule {

//    @Binds
//    @Singleton
//    abstract fun provideShareFirebasePresenter(shareFirebasePresenterImpl: ShareFirebasePresenterImpl): ShareFirebasePresenter

}