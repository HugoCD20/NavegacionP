package com.example.navegacion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAOdb {
    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User> // Cambiar a suspend

    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun consultarUno(userId: Int): User?

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Insert
    suspend fun insertUser(user: User) // Cambiar a suspend

    @Delete
    suspend fun delete(user: User) // Cambiar a suspend
}

