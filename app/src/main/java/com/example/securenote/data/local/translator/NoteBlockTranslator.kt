package com.example.securenote.data.local.translator

import com.example.securenote.data.local.entity.NoteBlockEntity
import com.example.securenote.domain.enum.BlockType
import com.example.securenote.domain.model.NoteBlock
import com.example.securenote.util.convertDateToLong
import com.example.securenote.util.toTimeDateString

fun NoteBlockEntity.toModel(): NoteBlock {
    return NoteBlock(
        id = id,
        noteId = noteId,
        order = order,
        type = BlockType.getBlockType(type),
        content = content,
        createdAt = createdAt.toTimeDateString(),
    )
}

fun NoteBlock.toEntity(): NoteBlockEntity {
    return NoteBlockEntity(
        id = id,
        noteId = noteId,
        order = order,
        type = type.value,
        content = content,
        createdAt = createdAt.convertDateToLong(),
    )
}