package com.example.securenote.data.local.translator

import com.example.securenote.data.local.entity.NoteEntity
import com.example.securenote.domain.enum.NoteType
import com.example.securenote.domain.model.Note
import com.example.securenote.domain.model.NoteBlock
import com.example.securenote.util.convertDateToLong
import com.example.securenote.util.toTimeDateString

fun NoteEntity.toModel(noteBlockList: List<NoteBlock> = emptyList<NoteBlock>()): Note {
    return Note(
        id = id,
        title = title,
        notesBlock = noteBlockList,
        createAt = createAt.toTimeDateString(),
        type = NoteType.getNoteTypeByValue(value = type)
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        createAt = createAt.convertDateToLong(),
        type = type.value
    )
}