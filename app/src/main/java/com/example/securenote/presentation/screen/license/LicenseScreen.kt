package com.example.securenote.presentation.screen.license

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar

@Composable
fun LicenseScreen( onBackPress: () -> Boolean) {
    val viewmodel: LicenseViewMode = hiltViewModel()
    BasePage(viewmodel) {
        Column(Modifier.fillMaxWidth()) {
            AppAppBar("License", onNavigationBtnClick = { onBackPress() })
        }
    }

}