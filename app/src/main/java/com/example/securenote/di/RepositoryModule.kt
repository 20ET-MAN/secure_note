package com.example.securenote.di

import com.example.securenote.data.AppSettingsRepositoryImpl
import com.example.securenote.data.NewsRepositoryImpl
import com.example.securenote.data.NoteBlockRepositoryImpl
import com.example.securenote.data.NoteRepositoryImpl
import com.example.securenote.data.local.datasource.AppSettingDataSource
import com.example.securenote.data.local.datasource.NoteBlockDataSource
import com.example.securenote.data.local.datasource.NoteDataSource
import com.example.securenote.data.local.datasourceImpl.AppSettingDataSourceImpl
import com.example.securenote.data.local.datasourceImpl.NoteBlockDataSourceImpl
import com.example.securenote.data.local.datasourceImpl.NoteDataSourceImpl
import com.example.securenote.data.remote.datasource.NewsRemoteDataSource
import com.example.securenote.data.remote.datasourceimpl.NewRemoteDataSourceImpl
import com.example.securenote.domain.repository.AppSettingsRepository
import com.example.securenote.domain.repository.NewsRepository
import com.example.securenote.domain.repository.NoteBlockRepository
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


    @Binds
    @Singleton
    abstract fun bindNoteBlockRepository(noteBlockRepositoryImpl: NoteBlockRepositoryImpl): NoteBlockRepository

    @Binds
    @Singleton
    abstract fun bindNewsRemoteDataSource(newRemoteDataSourceImpl: NewRemoteDataSourceImpl): NewsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}