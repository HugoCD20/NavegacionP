package com.example.navegacion

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface taskDao {
    @Query("SELECT * FROM Task")
    suspend fun getAll():List<Task>

    @Insert
    suspend fun insertTask(task: Task)
}