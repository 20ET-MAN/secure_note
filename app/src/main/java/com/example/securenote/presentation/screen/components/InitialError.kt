package com.example.securenote.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.securenote.R
import com.example.securenote.ui.theme.isLight

@Composable
fun InitialError(message: String? = null, onRetryClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(32.dp)
        ) {
            val image =
                if (MaterialTheme.colorScheme.isLight()) R.drawable.img_init_err_dark else R.drawable.img_init_err_light
            Image(painter = painterResource(image), contentDescription = null)
            Spacer(Modifier.height(24.dp))
            Text(
                message ?: stringResource(R.string.something_wrong),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                onRetryClick()
            },
        ) {
            Text(
                stringResource(R.string.retry),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}