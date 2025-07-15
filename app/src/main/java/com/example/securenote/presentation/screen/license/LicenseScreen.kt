package com.example.securenote.presentation.screen.license

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar

@Composable
fun LicenseScreen(viewmodel: LicenseViewMode = hiltViewModel(), onBackPress: () -> Boolean) {
    BasePage(viewmodel) {
        Column(Modifier.fillMaxWidth()) {
            AppAppBar(
                stringResource(R.string.licence_title),
                onNavigationBtnClick = { onBackPress() })
        }
    }

}