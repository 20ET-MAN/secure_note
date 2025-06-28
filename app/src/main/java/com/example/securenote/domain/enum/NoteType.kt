package com.example.securenote.domain.enum

enum class NoteType(val value: Int) {
    WORK(1), STUDY(2), COOK(3), MUSIC(4), GYM(5), OTHER(0);
    companion object{
        fun getNoteTypeByValue(value: Int): NoteType {
            return NoteType.entries.firstOrNull { it.value == value } ?: OTHER
        }
    }

}