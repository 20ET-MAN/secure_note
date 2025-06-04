package com.example.securenote.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AppSettingsRepository {
    fun isFirstLaunch(): Flow<Boolean>
    suspend fun setFirstLaunchDone()
    val isDarkMode: StateFlow<Boolean>
    suspend fun switchUIMode(value: Boolean)
}
