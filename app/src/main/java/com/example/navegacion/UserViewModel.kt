package com.example.navegacion
import android.util.Log
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
            "navegationdb"
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
    fun consultarUsuarioPorEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user: User? = withContext(Dispatchers.IO) {
                db.userDao().getUserByEmail(email)
            }
            onResult(user)
        }
    }

    fun insertarUsuario(user: User, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val existingEmailUser = db.userDao().getUserByEmail(user.email)
                    val existingUsernameUser = db.userDao().getUserByUsername(user.username)

                    when {
                        existingEmailUser != null -> {
                            withContext(Dispatchers.Main) {
                                onResult(false, "Correo ya registrado")
                            }
                        }
                        existingUsernameUser != null -> {
                            withContext(Dispatchers.Main) {
                                onResult(false, "Nombre de usuario ya registrado")
                            }
                        }
                        else -> {
                            user.hashPassword()
                            db.userDao().insertUser(user)
                            withContext(Dispatchers.Main) {
                                onResult(true, "Usuario registrado con éxito")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("DatabaseError", "Error al insertar usuario", e)
                    withContext(Dispatchers.Main) {
                        onResult(false, "Error al registrar usuario")
                    }
                }
            }
        }
    }


}
