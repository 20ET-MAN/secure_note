package com.example.securenote.di

import com.example.securenote.data.AppSettingsRepositoryImpl
import com.example.securenote.data.NoteRepositoryImpl
import com.example.securenote.data.local.AppSettingDataSource
import com.example.securenote.data.local.NoteBlockDataSource
import com.example.securenote.data.local.NoteDataSource
import com.example.securenote.data.local.datasource.AppSettingDataSourceImpl
import com.example.securenote.data.local.datasource.NoteBlockDataSourceImpl
import com.example.securenote.data.local.datasource.NoteDataSourceImpl
import com.example.securenote.domain.repository.AppSettingsRepository
import com.example.securenote.domain.repository.NoteRepository
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

    @Binds
    @Singleton
    abstract fun bindNoteDataSource(noteDataSourceImpl: NoteDataSourceImpl): NoteDataSource

    @Binds
    @Singleton
    abstract fun bindNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    abstract fun bindNoteBlockDataSource(noteBlockDataSourceImpl: NoteBlockDataSourceImpl): NoteBlockDataSource
}