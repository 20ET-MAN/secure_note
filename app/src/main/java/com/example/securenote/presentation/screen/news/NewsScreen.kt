package com.example.securenote.presentation.screen.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar

@Composable
fun NewsScreen(onBackPress: () -> Boolean) {
    val viewModel: NewsViewModel = hiltViewModel()
    val uiState = viewModel.news.collectAsState()
    BasePage(viewModel) {
        Column(Modifier.fillMaxWidth()) {
            AppAppBar(
                title = "News",
                onNavigationBtnClick = {
                    onBackPress()
                },
            )
            LazyColumn(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(space = 12.dp)
            ) {
                itemsIndexed(items = uiState.value.news) { index, newsItem ->
                    Column(modifier = Modifier.background(color = Color.Green)) {
                        Text(newsItem.title)
                        Text(newsItem.description)
                        Text(newsItem.category)
                        Text(newsItem.image)
                    }
                    if (index == uiState.value.news.lastIndex) {
                        Spacer(Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}