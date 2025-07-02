package com.example.securenote.domain.repository

import com.example.securenote.domain.model.NoteBlock
import kotlinx.coroutines.flow.Flow

interface NoteBlockRepository {
    fun getBlocks(noteId: Long): Flow<List<NoteBlock>>

    suspend fun updateBlock(block: NoteBlock)

    suspend fun insertBlock(block: NoteBlock)

    suspend fun deleteBlock(block: NoteBlock)
}