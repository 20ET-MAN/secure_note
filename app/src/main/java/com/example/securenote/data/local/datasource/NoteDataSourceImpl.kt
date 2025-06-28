package com.example.securenote.data.local.datasource

import com.example.securenote.data.local.NoteDataSource
import com.example.securenote.data.local.dao.NoteDao
import com.example.securenote.data.local.entity.NoteEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NoteDataSourceImpl @Inject constructor(private val noteDao: NoteDao) : NoteDataSource {
    override fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getNotes();
    }
}