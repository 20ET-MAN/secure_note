package com.example.securenote.data.local.datasource

import com.example.securenote.data.local.NoteBlockDataSource
import com.example.securenote.data.local.dao.NoteBlockDao
import com.example.securenote.data.translator.toModel
import com.example.securenote.domain.model.NoteBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteBlockDataSourceImpl @Inject constructor(private val noteBlockDao: NoteBlockDao) :
    NoteBlockDataSource {
    override fun getBlocks(noteId: Long): Flow<List<NoteBlock>> {
        return noteBlockDao.getBlocksByNodeId(noteId = noteId)
            .map { noteBlocks -> noteBlocks.map { noteBlock -> noteBlock.toModel() } }
    }

    override suspend fun updateBlock(block: NoteBlock) {
        noteBlockDao.updateBlock(block = block.toModel())
    }

    override suspend fun insertBlock(block: NoteBlock) {
        noteBlockDao.insertBlock(block = block.toModel())
    }

    override suspend fun deleteBlock(block: NoteBlock) {
        noteBlockDao.deleteBlock(block = block.toModel())
    }

    override suspend fun getBlocksPrevByNoteId(noteId: Long): List<NoteBlock> {
        return noteBlockDao.getBlocksPrevByNoteId(noteId = noteId).map { it.toModel() }
    }

}