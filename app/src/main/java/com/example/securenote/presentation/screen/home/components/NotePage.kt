package com.example.securenote.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.securenote.R
import com.example.securenote.domain.model.Note
import com.example.securenote.ui.theme.isLight

@Composable
fun NotePage(notes: List<Note>, onNoteEdits: (Long) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items = notes) { index, item ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .fillMaxSize()
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
                    Text(item.createAt, style = MaterialTheme.typography.titleSmall)
                    Icon(
                        painter = painterResource(R.drawable.ic_date_picker),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    item.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)
                val prevContent =
                    item.notesBlock.joinToString(separator = "\n") { it.content }
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    text = prevContent.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Start
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)
                Spacer(Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNoteEdits(item.id) }
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
        if (notes.isEmpty()) {
            item {
                val image =
                    if (MaterialTheme.colorScheme.isLight()) R.drawable.img_empty_note_light else R.drawable.img_empty_note_dark
                Box(
                    Modifier
                        .fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.5f)
                    )
                }
            }
        }
    }
}