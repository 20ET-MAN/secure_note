package com.example.securenote.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun HomeTabBar(
    tabs: List<TabItem>,
    selectedTabIndex: Int,
    noteCount: Int = 0,
    onTabSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = selectedTabIndex == index
            val color =
                if (selectedTabIndex == index) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.7f
                )
            var dividerWidth by remember {
                mutableStateOf(0.dp)
            }

            val localDensity = LocalDensity.current

            Column(
                modifier = Modifier
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 24.dp)
                    .onGloballyPositioned(onGloballyPositioned = { coordinates ->
                        dividerWidth = with(localDensity) { coordinates.size.width.toDp() }
                    }), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = tab.icon,
                        contentDescription = tab.label,
                        tint = color,
                    )
                    val countValue = if (noteCount == 0 || index != 0) "" else "(${noteCount})"
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${tab.label} $countValue",
                        style = MaterialTheme.typography.titleSmall.copy(color = color)
                    )
                }
                if (isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(dividerWidth)
                            .background(color, shape = RoundedCornerShape(1.dp))
                    )
                } else {
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

data class TabItem(val label: String, val icon: Painter)