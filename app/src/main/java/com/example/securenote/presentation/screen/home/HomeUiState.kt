package com.example.securenote.presentation.screen.home

import com.example.securenote.domain.model.Note

data class HomeUiState(
    val selectedTab: Int,
    val notes: List<Note> = emptyList<Note>(),
)
