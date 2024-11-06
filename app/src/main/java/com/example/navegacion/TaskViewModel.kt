package com.example.navegacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch

class TaskViewModel:ViewModel() {

    private lateinit var db: OrganizatDatabase

    init {
        // Inicializa la base de datos
        db = Room.databaseBuilder(
            MyApplication.getAppContext(), // Reemplaza esto con tu método para obtener el contexto
            OrganizatDatabase::class.java,
            "navegationdb"
        ).build()
    }

    fun insertTask(task: Task): Boolean{
        var  status: Boolean = true
        viewModelScope.launch {
            try {
                db.taskDao().insertTask(task)
            } catch (e: Exception) {
                status = false
            }
        }
        return status
    }
}