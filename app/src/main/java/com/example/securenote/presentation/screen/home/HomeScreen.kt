package com.example.securenote.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.presentation.base.BasePage

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val listState = rememberLazyListState()

    BasePage(viewModel = viewModel) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(state = listState) {
                items(count = 100) { index ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(if (index % 2 == 0) Color.Black else Color.Red)
                    )
                }
            }
            Text("Home screen ")
            Button(onClick = {
            }) {
                Text("Test")
            }
        }
    }
}