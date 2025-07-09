package com.example.securenote.domain.model

import com.example.securenote.domain.enum.BlockType
import com.example.securenote.util.toTimeDateString


data class NoteBlock(
    val id: Long = 0,
    val noteId: Long = 0,
    val order: Int = 1,
    val type: BlockType = BlockType.TEXT,
    val content: String = "",
    val createdAt: String =  System.currentTimeMillis().toTimeDateString()
)
