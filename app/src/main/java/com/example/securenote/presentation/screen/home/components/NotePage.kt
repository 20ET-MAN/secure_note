package com.example.securenote.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.securenote.R

@Composable
fun NotePage(dumpList: List<Int>, onNoteEdits: (String) -> Unit) {
    LazyColumn {
        itemsIndexed(items = dumpList) { index, item ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Date", style = MaterialTheme.typography.titleSmall)
                    Icon(
                        painter = painterResource(R.drawable.ic_date_picker),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text("Title", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
                Text(
                    modifier = Modifier.padding(vertical = 24.dp),
                    text = "content",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNoteEdits(item.toString()) }
                ) {
                    Text("Xem thêm")
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null
                    )
                }
            }

        }
        if (dumpList.isEmpty()) {
            item {
                Column {
                    Icon(imageVector = Icons.Default.Build, contentDescription = null)
                }
            }
        }
    }
}