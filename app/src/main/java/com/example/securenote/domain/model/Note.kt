package com.example.securenote.domain.model

import com.example.securenote.domain.enum.NoteType
import com.example.securenote.util.toTimeDateString

data class Note(
    val id: Long = 0,
    val title: String = "",
    val notesBlock: List<NoteBlock> = emptyList<NoteBlock>(),
    val createAt: String = System.currentTimeMillis().toTimeDateString(),
    val type: NoteType = NoteType.OTHER,
)
