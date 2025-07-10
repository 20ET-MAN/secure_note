package com.example.securenote.data

import com.example.securenote.data.local.datasource.AppSettingDataSource
import com.example.securenote.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val appSettingDataSource: AppSettingDataSource,
) :
    AppSettingsRepository {

    override fun isFirstLaunch(): Flow<Boolean> {
        return appSettingDataSource.isFirstLaunch()
    }

    override suspend fun setFirstLaunchDone() {
        appSettingDataSource.setFirstLaunchDone()
    }

    override val isDarkMode: StateFlow<Boolean> = appSettingDataSource.isDarkMode


    override suspend fun switchUIMode(value: Boolean) {
        appSettingDataSource.switchUIMode(value)
    }
}