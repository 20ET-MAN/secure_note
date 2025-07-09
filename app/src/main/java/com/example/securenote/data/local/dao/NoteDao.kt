package com.example.securenote.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.securenote.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Query(
        """
        SELECT * FROM note
        ORDER BY createAt DESC
    """
    )
    fun getNotes(): Flow<List<NoteEntity?>>

    @Query(
        """
        SELECT * FROM note
        WHERE id = :id
        ORDER BY createAt DESC
    """
    )
    fun getNote(id: Long): Flow<NoteEntity?>

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNote(id: Long)


}