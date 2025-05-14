package com.example.securenote.data.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.securenote.domain.repository.AppLaunchRepository
import com.example.securenote.util.Constant
import javax.inject.Inject

class AppLaunchRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    AppLaunchRepository {
    override suspend fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(Constant.KEY_FIRST_LAUNCH, true)
    }

    @SuppressLint("CommitPrefEdits")
    override suspend fun setFirstLaunchDone() {
        sharedPreferences.edit() { putBoolean(Constant.KEY_FIRST_LAUNCH, false) }
    }
}