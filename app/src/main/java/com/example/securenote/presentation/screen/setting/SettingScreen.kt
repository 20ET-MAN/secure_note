package com.example.securenote.presentation.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.BuildConfig
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onGoToLicense: () -> Unit,
    onBackPress: () -> Unit,
    onGoToNews: () -> Unit,
) {
    val isDarkMode = viewModel.isDarkMode.collectAsState()

    BasePage(viewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppAppBar("Setting", onNavigationBtnClick = onBackPress)
            SettingItem(label = "UI Mode") {
                Box {
                    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                        Switch(
                            checked = isDarkMode.value, onCheckedChange = { value ->
                                viewModel.switchAppMode(value)
                            }
                        )
                    }
                }
            }
            SettingItem(label = "License", onItemClick = onGoToLicense)
            SettingItem(label = "News", onItemClick = onGoToNews)

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

@Composable
fun SettingItem(
    label: String,
    onItemClick: () -> Unit = {},
    actionIcon: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable(enabled = actionIcon == null) {
                onItemClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            if (actionIcon == null) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                )
            } else {
                actionIcon.invoke()
            }
        }
        HorizontalDivider()
    }
}
