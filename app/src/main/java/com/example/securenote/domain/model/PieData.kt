package com.example.securenote.domain.model

import com.example.securenote.domain.enum.NoteType

data class PieData(
    val value: Float,
    val noteType: NoteType,
)
