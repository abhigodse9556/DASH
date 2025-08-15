package com.project.mypersonalassistant.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.mypersonalassistant.roomDB.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username OR email = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username OR email = :username")
    suspend fun getUserByUsernameOrEmail(username: String): User?

    // Get the security question and answer for verification
    @Query("SELECT question, answer FROM users WHERE username = :username OR email = :username")
    suspend fun getSecurityQA(username: String): SecurityQA?

    // Update password
    @Query("UPDATE users SET password = :newPassword WHERE username = :username OR email = :username")
    suspend fun updatePassword(username: String, newPassword: String)
}

// DTO for only question and answer
data class SecurityQA(
    val question: String,
    val answer: String
)