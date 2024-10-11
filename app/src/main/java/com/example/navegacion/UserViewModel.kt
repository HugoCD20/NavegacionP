package com.example.navegacion
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {
    private lateinit var db: OrganizatDatabase

    init {
        // Inicializa la base de datos
        db = Room.databaseBuilder(
            MyApplication.getAppContext(), // Reemplaza esto con tu método para obtener el contexto
            OrganizatDatabase::class.java,
            "database-name"
        ).build()
    }

    fun consultarUsuario(id: Int, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user: User? = withContext(Dispatchers.IO) {
                db.userDao().consultarUno(id)
            }
            onResult(user)
        }
    }
}
