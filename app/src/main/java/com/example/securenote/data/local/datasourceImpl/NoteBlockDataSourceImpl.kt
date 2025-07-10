package com.example.securenote.data.local.datasourceImpl

import com.example.securenote.data.local.dao.NoteBlockDao
import com.example.securenote.data.local.datasource.NoteBlockDataSource
import com.example.securenote.data.local.entity.NoteBlockEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteBlockDataSourceImpl @Inject constructor(private val noteBlockDao: NoteBlockDao) :
    NoteBlockDataSource {
    override fun getBlocks(noteId: Long): Flow<List<NoteBlockEntity>> {
        return noteBlockDao.getBlocksByNodeId(noteId = noteId).map { it.filterNotNull() }
    }

    override suspend fun updateBlock(block: NoteBlockEntity) {
        noteBlockDao.updateBlock(block = block)
    }

    override suspend fun insertBlock(block: NoteBlockEntity) {
        noteBlockDao.insertBlock(block = block)
    }

    override suspend fun deleteBlock(block: NoteBlockEntity) {
        noteBlockDao.deleteBlock(block = block)
    }

    override suspend fun getBlocksPrevByNoteId(noteId: Long): List<NoteBlockEntity> {
        return noteBlockDao.getBlocksPrevByNoteId(noteId = noteId).filterNotNull()
    }

    override fun getBlocksByTime(startTime: Long, endTime: Long): Flow<List<NoteBlockEntity>> {
        return noteBlockDao.getBlocksByTime(startTime = startTime, endTime = endTime).map { it.filterNotNull() }
    }

}