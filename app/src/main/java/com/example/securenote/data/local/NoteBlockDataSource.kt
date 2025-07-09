package com.example.securenote.data.local

import com.example.securenote.data.local.entity.NoteBlockEntity
import kotlinx.coroutines.flow.Flow

interface NoteBlockDataSource {
    fun getBlocks(noteId: Long): Flow<List<NoteBlockEntity>>

    suspend fun updateBlock(block: NoteBlockEntity)

    suspend fun insertBlock(block: NoteBlockEntity)

    suspend fun deleteBlock(block: NoteBlockEntity)

    suspend fun getBlocksPrevByNoteId(noteId: Long): List<NoteBlockEntity>

    fun getBlocksByTime(startTime: Long, endTime: Long): Flow<List<NoteBlockEntity>>
}