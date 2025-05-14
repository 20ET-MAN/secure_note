package com.example.securenote.presentation.helper.biometric

import androidx.appcompat.app.AppCompatActivity
import com.example.securenote.presentation.helper.biometric.BiometricHelperImpl.BiometricResult
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.Flow

interface BiometricHelper {
    val promptResults: Flow<BiometricResult?>
    fun showBiometricPrompt(
        activity: AppCompatActivity,
        title: String,
        description: String
    )
    fun resetResult()
}


@EntryPoint
@InstallIn(ActivityComponent::class)
interface BiometricEntryPoint {
    fun biometricHelper(): BiometricHelper
}
