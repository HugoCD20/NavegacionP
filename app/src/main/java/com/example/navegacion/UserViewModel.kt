package com.example.navegacion
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
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
    fun consultarUsuarios(onResult: (List<User>) -> Unit) {
        viewModelScope.launch {
            val users: List<User> = withContext(Dispatchers.IO) {
                val userDao = db.userDao()
                userDao.getAll()
            }
            onResult(users)
        }
    }

    fun consultarUsuario(id: Int, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user: User? = withContext(Dispatchers.IO) {
                db.userDao().consultarUno(id)
            }
            onResult(user)
        }
    }
    fun insertarUsuario(user:User){
        viewModelScope  .launch {
            val newUser = user
            newUser.hashPassword()
            withContext(Dispatchers.IO) {
                try {
                    db.userDao().insertUser(newUser)
                    Log.d("InsertUser", "Usuario insertado: $newUser")
                } catch (e: Exception) {
                    Log.e("DatabaseError", "Error al insertar usuario", e)
                }
            }
        }
    }
}
