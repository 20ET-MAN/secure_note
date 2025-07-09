package com.example.securenote.data.local.datasource

import com.example.securenote.data.local.NoteDataSource
import com.example.securenote.data.local.dao.NoteDao
import com.example.securenote.data.local.entity.NoteEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteDataSourceImpl @Inject constructor(private val noteDao: NoteDao) : NoteDataSource {
    override fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getNotes().map { it.filterNotNull() };
    }

    override suspend fun insertNote(note: NoteEntity): Long {
        return noteDao.insertNote(note = note)
    }

    override fun getNote(id: Long): Flow<NoteEntity?> {
        return noteDao.getNote(id)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note = note)
    }

    override suspend fun deleteNote(id: Long) {
        noteDao.deleteNote(id)
    }


}