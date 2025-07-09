package com.example.securenote.domain.enum

import androidx.compose.ui.graphics.Color

enum class NoteType(val value: Int, val typeName: String, val color: Color) {
    WORK(1, "Work", Color.Blue),
    STUDY(2, "Study", Color.Yellow),
    COOK(3, "Cook", Color.Red),
    MUSIC(4, "Music", Color.Green),
    GYM(5, "Gym", Color.DarkGray),
    OTHER(0, "", Color.Black);

    companion object {
        fun getNoteTypeByValue(value: Int): NoteType {
            return NoteType.entries.firstOrNull { it.value == value } ?: OTHER
        }
    }

}