package com.example.securenote.domain.model

import com.example.securenote.domain.enum.BlockType


data class NoteBlock(
    val noteId: Long,
    val order: Int,
    val type: BlockType,
    val content: String,
)
