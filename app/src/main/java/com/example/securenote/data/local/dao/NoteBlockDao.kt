package com.example.securenote.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.securenote.data.local.entity.NoteBlockEntity

@Dao
interface NoteBlockDao {

    @Query(
        """
        SELECT * FROM note_block
        WHERE noteId = :noteId
        ORDER BY `order` ASC
    """
    )
    fun getBlocksByNodeId(noteId: Long): List<NoteBlockEntity>
}