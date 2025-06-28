package com.example.securenote.data

import com.example.securenote.data.local.NoteBlockDataSource
import com.example.securenote.data.local.NoteDataSource
import com.example.securenote.data.translator.toModel
import com.example.securenote.domain.model.Note
import com.example.securenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val noteBlockDataSource: NoteBlockDataSource,
) :
    NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDataSource.getNotes().map { noteEntities ->
            noteEntities.map { noteEntity ->
                val noteBlocks = noteBlockDataSource.getNoteBlockList(noteEntity.id)
                noteEntity.toModel(noteBlocks)
            }.toList()
        }
    }
}