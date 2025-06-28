package com.example.securenote.data.local

import com.example.securenote.data.local.entity.NoteBlockEntity

interface NoteBlockDataSource {
    fun getNoteBlockList(id: Long): List<NoteBlockEntity>
}