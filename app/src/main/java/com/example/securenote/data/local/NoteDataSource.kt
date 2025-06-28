package com.example.securenote.data.local

import com.example.securenote.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getNotes(): Flow<List<NoteEntity>>
}