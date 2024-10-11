package com.example.navegacion

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.navegacion.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Inicio : Fragment(R.layout.fragment_inicio) {
    private lateinit var db: OrganizatDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            requireContext(),
            OrganizatDatabase::class.java, "database-name"
        ).build()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Busca la vista por ID después de que la vista ha sido creada
        val redireccionI: Button = view.findViewById(R.id.boton1)

        redireccionI.setOnClickListener {
            parentFragmentManager.popBackStack()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Item1Fragment())
                .addToBackStack(null)
                .commit()

            lifecycleScope.launch {
                val userDao = db.userDao()
                val users: List<User> = withContext(Dispatchers.IO) {
                    userDao.getAll()
                }
                // Aquí puedes usar la lista de usuarios
                Log.d("Users", users.toString()) // Muestra la lista de usuarios en Logcat
            }
        }
    }
}
