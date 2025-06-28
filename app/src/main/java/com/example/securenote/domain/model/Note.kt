package com.example.securenote.domain.model

import com.example.securenote.domain.enum.NoteType

data class Note(
    val id: Long,
    val title: String,
    val notesBlock: List<NoteBlock> = emptyList<NoteBlock>(),
    val createAt: String,
    val type: NoteType,
)
