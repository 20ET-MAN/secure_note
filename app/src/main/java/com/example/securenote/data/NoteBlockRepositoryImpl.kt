package com.example.securenote.data

import com.example.securenote.data.local.NoteBlockDataSource
import com.example.securenote.domain.model.NoteBlock
import com.example.securenote.domain.repository.NoteBlockRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NoteBlockRepositoryImpl @Inject constructor(private val noteBlockDataSource: NoteBlockDataSource) :
    NoteBlockRepository {
    override fun getBlocks(noteId: Long): Flow<List<NoteBlock>> {
        return noteBlockDataSource.getBlocks(noteId)
    }

    override suspend fun updateBlock(block: NoteBlock) {
        noteBlockDataSource.updateBlock(block = block)
    }

    override suspend fun insertBlock(block: NoteBlock) {
        noteBlockDataSource.insertBlock(block = block)
    }

    override suspend fun deleteBlock(block: NoteBlock) {
        noteBlockDataSource.deleteBlock(block = block)
    }


}