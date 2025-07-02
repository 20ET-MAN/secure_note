package com.example.securenote.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.securenote.data.local.entity.NoteBlockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteBlockDao {

    @Query(
        """
        SELECT * FROM note_block
        WHERE noteId = :noteId
        ORDER BY `order` ASC
    """
    )
    fun getBlocksByNodeId(noteId: Long): Flow<List<NoteBlockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlock(block: NoteBlockEntity)

    @Update
    suspend fun updateBlock(block: NoteBlockEntity)

    @Delete
    suspend fun deleteBlock(block: NoteBlockEntity)

    @Query(
        """
        SELECT * FROM note_block
        WHERE noteId = :noteId
        ORDER BY `order` ASC
        LIMIT 3
    """
    )
    suspend fun getBlocksPrevByNoteId(noteId: Long): List<NoteBlockEntity>
}