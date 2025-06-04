package com.example.securenote.presentation.helper.biometric

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import com.example.securenote.R
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
            promptInfo.setNegativeButtonText(context.getString(R.string.cancel))
        }

        when (manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                setErrorResult(msg = context.getString(R.string.biometric_hardware_unavailable))
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                setErrorResult(msg = context.getString(R.string.biometric_feature_unavailable))
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
                    setErrorResult(errString.toString(), errorCode)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultState.value = BiometricResult.AuthenticationSuccess
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    setErrorResult(msg = context.getString(R.string.authentication_failed))
                }
            }
        )
        prompt.authenticate(promptInfo.build())
    }

    fun setErrorResult(msg: String, errorCode: Int = 0) {
        resultState.value = BiometricResult.AuthenticationError(
            errorMsg = msg,
            errorCode = errorCode
        )
    }

    override fun resetResult() {
        resultState.value = null
    }

    sealed interface BiometricResult {
        data class AuthenticationError(val errorMsg: String, val errorCode: Int) :
            BiometricResult

        data object AuthenticationSuccess : BiometricResult
        data object AuthenticationNotSet : BiometricResult
    }
}