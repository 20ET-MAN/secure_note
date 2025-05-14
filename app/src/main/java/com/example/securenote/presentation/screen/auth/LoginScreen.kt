package com.example.securenote.presentation.screen.auth

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.presentation.base.BaseDialog
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.helper.biometric.BiometricEntryPoint
import com.example.securenote.presentation.helper.biometric.BiometricHelperImpl.BiometricResult
import dagger.hilt.android.EntryPointAccessors

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    val activity = context as AppCompatActivity

    val biometricHelper = remember {
        EntryPointAccessors
            .fromActivity(activity, BiometricEntryPoint::class.java)
            .biometricHelper()
    }

    val result by biometricHelper.promptResults.collectAsState(initial = null)

    fun startAuthentication() {
        biometricHelper.showBiometricPrompt(
            activity = activity,
            title = "Xác thực bảo mật",
            description = "Quý khách vui lòng xác thực để sử dụng ứng dụng"
        )
    }

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            startAuthentication()
        }
    )


    var isShowGotoSettingBiometricDialog by remember { mutableStateOf(false) }

    LaunchedEffect(result) {
        when (result) {
            is BiometricResult.AuthenticationError -> {
                viewModel.handlerError((result as BiometricResult.AuthenticationError).error)
            }

            BiometricResult.AuthenticationFailed -> {
                viewModel.handlerError("Authentication failed")
            }

            BiometricResult.AuthenticationNotSet -> {
                isShowGotoSettingBiometricDialog = true
            }

            BiometricResult.AuthenticationSuccess -> {
                onNavigateToHome()
            }

            BiometricResult.FeatureUnavailable -> {
                viewModel.handlerError("Feature unavailable")
            }

            BiometricResult.HardwareUnavailable -> {
                viewModel.handlerError("Hardware unavailable")
            }

            null -> {}
        }

        biometricHelper.resetResult()
    }

    fun gotoBiometricSetting() {
        if (Build.VERSION.SDK_INT >= 30) {
            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
            }
            enrollLauncher.launch(enrollIntent)
        }
        isShowGotoSettingBiometricDialog = false
    }



    BasePage(viewModel) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {
                startAuthentication()
            }) {
                Text(
                    "Đăng nhập",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }


        BaseDialog(show = isShowGotoSettingBiometricDialog, onDismiss = {
        }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thông báo", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Vui lòng cài đặt sinh trắc học để sử dụng ứng dụng.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    gotoBiometricSetting()
                }) {
                    Text(
                        "Cài đặt sinh trắc học",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

    }
}


