package com.example.securenote.data

import com.example.securenote.data.local.datasource.NoteBlockDataSource
import com.example.securenote.data.local.datasource.NoteDataSource
import com.example.securenote.data.local.translator.toEntity
import com.example.securenote.data.local.translator.toModel
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
    override fun getPrevNotes(): Flow<List<Note>> {
        return noteDataSource.getNotes().map { noteEntities ->
            noteEntities.map { noteEntity ->
                val noteBlocks =
                    noteBlockDataSource.getBlocksPrevByNoteId(noteEntity.id).map { it.toModel() }
                noteEntity.toModel(noteBlocks)
            }.toList()
        }
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDataSource.insertNote(note.toEntity())
    }

    override fun getNote(id: Long): Flow<Note?> {
        return noteDataSource.getNote(id).map { it?.toModel() }
    }

    override suspend fun updateNote(note: Note) {
        noteDataSource.updateNote(note = note.toEntity())
    }

    override suspend fun deleteNote(id: Long) {
        noteDataSource.deleteNote(id)
    }
}