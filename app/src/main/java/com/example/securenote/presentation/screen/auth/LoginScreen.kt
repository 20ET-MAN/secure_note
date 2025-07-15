package com.example.securenote.presentation.screen.auth

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.R
import com.example.securenote.presentation.base.BaseDialog
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.dialog.CommonErrorDialog
import com.example.securenote.presentation.helper.biometric.BiometricEntryPoint
import com.example.securenote.presentation.helper.biometric.BiometricHelperImpl.BiometricResult
import com.example.securenote.presentation.helper.biometric.BiometricHelperImpl.BiometricResult.AuthenticationError
import dagger.hilt.android.EntryPointAccessors


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as AppCompatActivity


    val biometricHelper = remember {
        EntryPointAccessors
            .fromActivity(activity, BiometricEntryPoint::class.java)
            .biometricHelper()
    }

    var biometricError = remember { mutableStateOf<String?>(null) }

    val result by biometricHelper.promptResults.collectAsState(initial = null)

    fun startAuthentication() {
        biometricHelper.showBiometricPrompt(
            activity = activity,
            title = context.getString(R.string.biometric_prompt_title),
            description = context.getString(R.string.biometric_prompt_desc),
        )
    }

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )

    var isShowGotoSettingBiometricDialog by remember { mutableStateOf(false) }

    LaunchedEffect(result) {
        when (result) {
            is AuthenticationError -> {
                val error = (result as AuthenticationError)
                biometricError.value = context.getString(
                    R.string.biometric_authentication_error,
                    error.errorMsg,
                    error.errorCode.toString()
                )
            }

            BiometricResult.AuthenticationNotSet -> {
                isShowGotoSettingBiometricDialog = true
            }

            BiometricResult.AuthenticationSuccess -> {
                onNavigateToHome()
            }

            null -> {}
        }

        biometricHelper.resetResult()
    }

    fun gotoBiometricSetting() {
        val intent: Intent = if (Build.VERSION.SDK_INT >= 30) {
            Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
            }
        } else {
            Intent(Settings.ACTION_SECURITY_SETTINGS)
        }

        enrollLauncher.launch(intent)
        isShowGotoSettingBiometricDialog = false
    }

    BasePage(viewModel) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.login_sc_title),
                style = MaterialTheme.typography.titleLarge,
            )

            Button(shape = MaterialTheme.shapes.small, onClick = {
                startAuthentication()
            }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_biometrics),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.login_sc_authentication_btn),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }


        BaseDialog(show = isShowGotoSettingBiometricDialog, onDismiss = {
        }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.login_sc_biometric_error_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(R.string.login_sc_biometric_error_description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        gotoBiometricSetting()
                    }) {
                    Text(
                        stringResource(R.string.login_sc_biometric_error_btn),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        CommonErrorDialog(
            errorMessage = biometricError.value,
            show = biometricError.value != null,
            positiveButtonClick = { biometricError.value = null },
            showNegativeButton = false,
            positiveButtonText = stringResource(R.string.close)
        )
    }
}


