package com.example.securenote.presentation.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securenote.presentation.base.BaseDialog

@Composable
fun CommonErrorDialog(show: Boolean, onCancel: () -> Unit, onSummit: () -> Unit) {
    BaseDialog(show = show) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Something wrong !!!")
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    onCancel()
                }) {
                    Text("Cancel")
                }

                Button(onClick = {
                    onSummit()
                }) {
                    Text("Retry")
                }
            }
        }
    }
}
