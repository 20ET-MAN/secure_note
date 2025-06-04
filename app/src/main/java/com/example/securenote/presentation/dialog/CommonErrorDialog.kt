package com.example.securenote.presentation.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.securenote.R
import com.example.securenote.presentation.base.BaseDialog

@Composable
fun CommonErrorDialog(
    show: Boolean,
    errorMessage: String?,
    showNegativeButton: Boolean = true,
    negativeButtonText: String = stringResource(R.string.cancel),
    positiveButtonText: String = stringResource(R.string.retry),
    negativeButtonClick: () -> Unit = {},
    positiveButtonClick: () -> Unit
) {
    BaseDialog(show = show) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                errorMessage ?: stringResource(R.string.something_wrong),
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (showNegativeButton) {
                    Button(onClick = {
                        negativeButtonClick()
                    }) {
                        Text(negativeButtonText)
                    }
                }

                Button(onClick = {
                    positiveButtonClick()
                }) {
                    Text(positiveButtonText)
                }
            }
        }
    }
}
