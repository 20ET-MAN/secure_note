package com.example.securenote.presentation.screen.notedetail

import com.example.securenote.domain.model.Note
import com.example.securenote.domain.model.NoteBlock

data class NoteDetailUiState(
    val note: Note= Note(title = ""),
    val noteBlock: List<NoteBlock> = emptyList<NoteBlock>(),
    val savedNote: Boolean= false
)
