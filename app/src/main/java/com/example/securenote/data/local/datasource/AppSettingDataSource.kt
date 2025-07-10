package com.example.securenote.data.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AppSettingDataSource {
     fun isFirstLaunch(): Flow<Boolean>
     val isDarkMode: StateFlow<Boolean>
     suspend fun setFirstLaunchDone()
     suspend fun switchUIMode(value: Boolean)
}