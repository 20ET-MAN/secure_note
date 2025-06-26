package com.example.securenote.di

import com.example.securenote.data.local.AppSettingDataSource
import com.example.securenote.data.local.datasource.AppSettingDataSourceImpl
import com.example.securenote.data.AppSettingsRepositoryImpl
import com.example.securenote.domain.repository.AppSettingsRepository
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
    abstract fun bindAppLaunchDataSource(appSettingDataSourceImpl: AppSettingDataSourceImpl): AppSettingDataSource

    @Binds
    @Singleton
    abstract fun bindAppLaunchRepository(appLaunchRepositoryImpl: AppSettingsRepositoryImpl): AppSettingsRepository
}