package com.example.securenote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.securenote.data.local.dao.NoteBlockDao
import com.example.securenote.data.local.dao.NoteDao
import com.example.securenote.data.local.dao.UserDao
import com.example.securenote.data.local.entity.NoteBlockEntity
import com.example.securenote.data.local.entity.NoteEntity
import com.example.securenote.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, NoteEntity::class, NoteBlockEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun notBlockDao(): NoteBlockDao
}