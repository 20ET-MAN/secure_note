package com.example.securenote.domain.repository

import com.example.securenote.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getPrevNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note): Long
    fun getNote(id: Long): Flow<Note?>
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
}