package com.example.securenote.presentation.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.BuildConfig
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar

@Composable
fun SettingScreen(onGoToLicense: () -> Unit, onBackPress: () -> Unit) {
    val viewModel: SettingViewModel = hiltViewModel()
    val isDarkMode = viewModel.isDarkMode.collectAsState()
    BasePage(viewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppAppBar("Setting", onNavigationBtnClick = onBackPress)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("UI Mode", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = isDarkMode.value, onCheckedChange = { value ->
                        viewModel.switchAppMode(value)
                    })

                }
                HorizontalDivider()
            }


        }
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                "App version: ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
