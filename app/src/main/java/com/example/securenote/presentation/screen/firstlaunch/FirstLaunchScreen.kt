package com.example.securenote.presentation.screen.firstlaunch

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import kotlinx.coroutines.launch

@Composable
fun FirstLaunchScreen(
    viewModel: FirstLaunchViewModel = hiltViewModel(),
    onGoToLogin: () -> Unit,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isDeviceSecure = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val text = if (isDeviceSecure.value) stringResource(R.string.first_launch_secure_done_msg)
    else stringResource(R.string.first_launch_setup_biometrics_or_pin)
    val buttonText =
        if (isDeviceSecure.value) stringResource(R.string.next) else stringResource(R.string.first_launch_security_configuration)
    val icon = if (isDeviceSecure.value) Icons.Default.Check else Icons.Default.Warning
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            val keyguard = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            isDeviceSecure.value = keyguard.isDeviceSecure
        }
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
    }

    BasePage(viewModel = viewModel) { vm ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 64.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
            Spacer(modifier = Modifier.height(98.dp))
            Icon(icon, contentDescription = null, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                if (isDeviceSecure.value) {
                    scope.launch {
                        viewModel.setFirstLaunchDone()
                        onGoToLogin()
                    }
                } else {
                    gotoBiometricSetting()
                }
            }) {
                Text(buttonText)
            }
        }
    }
}