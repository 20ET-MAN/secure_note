package com.example.securenote.data.translator

import com.example.securenote.data.local.entity.NoteBlockEntity
import com.example.securenote.domain.enum.BlockType
import com.example.securenote.domain.model.NoteBlock

fun NoteBlockEntity.toModel(): NoteBlock {
    return NoteBlock(
        id = id,
        noteId = noteId,
        order = order,
        type = BlockType.getBlockType(type),
        content = content
    )
}

fun NoteBlock.toModel(): NoteBlockEntity {
    return NoteBlockEntity(
        id = id,
        noteId = noteId,
        order = order,
        type = type.value,
        content = content
    )
}