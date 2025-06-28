package com.example.securenote.data.translator

import com.example.securenote.data.local.entity.NoteBlockEntity
import com.example.securenote.data.local.entity.NoteEntity
import com.example.securenote.domain.enum.NoteType
import com.example.securenote.domain.model.Note
import com.example.securenote.util.toTimeDateString

fun NoteEntity.toModel(noteBlockList: List<NoteBlockEntity> = emptyList<NoteBlockEntity>()): Note {
    return Note(
        id = id,
        title = title,
        notesBlock = noteBlockList.map { it.toModel() }.toList(),
        createAt = createdAt.toTimeDateString(),
        type = NoteType.getNoteTypeByValue(value = type)
    )
}