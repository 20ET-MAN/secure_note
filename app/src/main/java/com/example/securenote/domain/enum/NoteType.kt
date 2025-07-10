package com.example.securenote.domain.enum

import androidx.compose.ui.graphics.Color

enum class NoteType(val value: Int, val typeName: String, val color: Color) {
    WORK(1, "Work", Color(0xFFF66D44)),
    STUDY(2, "Study", Color(0xFFFEAE65)),
    COOK(3, "Cook", Color(0xFFE6F69D)),
    MUSIC(4, "Music", Color(0xFFAADEA7)),
    GYM(5, "Gym", Color(0xFF64C2A6)),
    OTHER(0, "Other", Color(0xFF2D87BB));

    companion object {
        fun getNoteTypeByValue(value: Int): NoteType {
            return NoteType.entries.firstOrNull { it.value == value } ?: OTHER
        }
    }

}