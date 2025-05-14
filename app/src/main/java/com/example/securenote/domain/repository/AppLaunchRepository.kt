package com.example.securenote.domain.repository

interface AppLaunchRepository {
    suspend fun isFirstLaunch(): Boolean
    suspend fun setFirstLaunchDone()
}
