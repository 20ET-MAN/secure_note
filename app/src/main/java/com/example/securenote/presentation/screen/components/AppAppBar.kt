package com.example.securenote.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.securenote.R

@Composable
fun AppAppBar(
    title: String,
    isShowNavigationBtn: Boolean = true,
    actionButton: (@Composable () -> Unit)?= null,
    onActionBtnClick: () -> Unit = {},
    onNavigationBtnClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            if (isShowNavigationBtn) {
                Icon(
                    painter = painterResource(R.drawable.ic_line_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onNavigationBtnClick)
                )
                Spacer(Modifier.width(24.dp))
            }
            Text(title, style = MaterialTheme.typography.titleLarge)
        }
        actionButton?.let {
            Box(modifier = Modifier.clickable(onClick = onActionBtnClick)){
                it.invoke()
            }
        }
    }
}