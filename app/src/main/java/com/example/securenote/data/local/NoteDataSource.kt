package com.example.securenote.data.local

import com.example.securenote.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getNotes(): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity): Long
    fun getNote(id: Long): Flow<NoteEntity?>
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(id: Long)
}