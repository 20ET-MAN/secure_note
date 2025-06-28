package com.example.securenote.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.securenote.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query(
        """
        SELECT * FROM note
        ORDER BY createdAt DESC
    """
    )
    fun getNotes(): Flow<List<NoteEntity>>
}