package com.example.securenote.di

import com.example.securenote.data.repository.AppLaunchRepositoryImpl
import com.example.securenote.domain.repository.AppLaunchRepository
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
    abstract fun bindAppLaunchRepository(appLaunchRepositoryImpl: AppLaunchRepositoryImpl): AppLaunchRepository
}