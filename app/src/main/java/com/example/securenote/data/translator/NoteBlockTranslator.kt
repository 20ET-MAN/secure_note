package com.example.securenote.data.translator

import com.example.securenote.data.local.entity.NoteBlockEntity
import com.example.securenote.domain.enum.BlockType
import com.example.securenote.domain.model.NoteBlock

fun NoteBlockEntity.toModel(): NoteBlock {
    return NoteBlock(
        noteId = noteId,
        order = order,
        type = BlockType.getBlockType(type),
        content = content
    )
}