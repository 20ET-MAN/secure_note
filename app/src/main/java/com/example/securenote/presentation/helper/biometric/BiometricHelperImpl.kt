package com.example.securenote.presentation.helper.biometric

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class BiometricHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : BiometricHelper {
    private val resultState = MutableStateFlow<BiometricResult?>(null)
    override val promptResults: Flow<BiometricResult?> = resultState
    override fun showBiometricPrompt(
        activity: AppCompatActivity,
        title: String,
        description: String
    ) {
        val manager = BiometricManager.from(context)
        val authenticators = if (Build.VERSION.SDK_INT >= 30) {
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        } else BIOMETRIC_STRONG

        val promptInfo = PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)

        if (Build.VERSION.SDK_INT < 30) {
            promptInfo.setNegativeButtonText("Cancel")
        }

        when (manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultState.value = BiometricResult.HardwareUnavailable
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultState.value = BiometricResult.FeatureUnavailable
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultState.value = BiometricResult.AuthenticationNotSet
                return
            }

            else -> Unit
        }

        val prompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultState.value = BiometricResult.AuthenticationError(errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultState.value = BiometricResult.AuthenticationSuccess
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultState.value = BiometricResult.AuthenticationFailed
                }
            }
        )
        prompt.authenticate(promptInfo.build())
    }

    override fun resetResult() {
        resultState.value = null
    }

    sealed interface BiometricResult {
        data object HardwareUnavailable : BiometricResult
        data object FeatureUnavailable : BiometricResult
        data class AuthenticationError(val error: String) : BiometricResult
        data object AuthenticationFailed : BiometricResult
        data object AuthenticationSuccess : BiometricResult
        data object AuthenticationNotSet : BiometricResult
    }
}