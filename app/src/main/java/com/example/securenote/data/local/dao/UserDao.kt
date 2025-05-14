package com.example.securenote.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.securenote.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun observeUser(userId: String): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET isBiometricEnabled = :isEnabled WHERE id = :userId")
    suspend fun updateBiometricStatus(userId: String, isEnabled: Boolean)

    @Query("UPDATE users SET lastLoginTimestamp = :timestamp WHERE id = :userId")
    suspend fun updateLastLogin(userId: String, timestamp: Long)
}