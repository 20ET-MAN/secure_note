package com.example.securenote.presentation.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BaseDialog(show: Boolean, onDismiss: () -> Unit = {}, content: @Composable (() -> Unit)) {
    if (show) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val maxDialogHeight = screenHeight / 2
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 8.dp,
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(max = maxDialogHeight)
                        .fillMaxWidth()
                        .padding(24.dp), contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}
