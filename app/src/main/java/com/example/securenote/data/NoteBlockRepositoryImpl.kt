package com.example.securenote.data

import com.example.securenote.data.local.datasource.NoteBlockDataSource
import com.example.securenote.data.local.translator.toEntity
import com.example.securenote.data.local.translator.toModel
import com.example.securenote.domain.model.NoteBlock
import com.example.securenote.domain.repository.NoteBlockRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteBlockRepositoryImpl @Inject constructor(private val noteBlockDataSource: NoteBlockDataSource) :
    NoteBlockRepository {
    override fun getBlocks(noteId: Long): Flow<List<NoteBlock>> {
        return noteBlockDataSource.getBlocks(noteId).map { it.map { it.toModel() } }
    }

    override suspend fun updateBlock(block: NoteBlock) {
        noteBlockDataSource.updateBlock(block = block.toEntity())
    }

    override suspend fun insertBlock(block: NoteBlock) {
        noteBlockDataSource.insertBlock(block = block.toEntity())
    }

    override suspend fun deleteBlock(block: NoteBlock) {
        noteBlockDataSource.deleteBlock(block = block.toEntity())
    }

    override fun getBlocksByTime(
        startTime: Long,
        endTime: Long,
    ): Flow<List<NoteBlock>> {
        return noteBlockDataSource.getBlocksByTime(startTime = startTime, endTime = endTime)
            .map { it.map { it.toModel() } }
    }


}