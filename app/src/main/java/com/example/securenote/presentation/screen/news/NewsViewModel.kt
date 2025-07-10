package com.example.securenote.presentation.screen.news

import com.example.securenote.domain.repository.NewsRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class NewsViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val newsRepository: NewsRepository,
) : BaseViewModel(errorHandler) {

    private var _news = MutableStateFlow(NewsUiState())
    val news: StateFlow<NewsUiState> = _news.asStateFlow()

    init {
        launchInitialData {
            val newsData = newsRepository.getNews()
            _news.update { it.copy(news = newsData) }
        }
    }
}