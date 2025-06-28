package com.example.securenote.data.local.datasource

import com.example.securenote.data.local.NoteBlockDataSource
import com.example.securenote.data.local.dao.NoteBlockDao
import com.example.securenote.data.local.entity.NoteBlockEntity
import javax.inject.Inject

class NoteBlockDataSourceImpl @Inject constructor(private val noteBlockDao: NoteBlockDao) :
    NoteBlockDataSource {
    override fun getNoteBlockList(id: Long): List<NoteBlockEntity> {
        return noteBlockDao.getBlocksByNodeId(id);
    }

}