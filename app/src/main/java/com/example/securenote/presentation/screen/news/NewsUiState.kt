package com.example.securenote.presentation.screen.news

import com.example.securenote.domain.model.News

data class NewsUiState(
    val news: List<News> = emptyList<News>(),
)
