package com.example.navegacion

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Item1Fragment : Fragment(R.layout.fragment_item1) {
    private val userViewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boton1: Button=view.findViewById(R.id.botonI)

        boton1.setOnClickListener {
            val id = 1 // ID del usuario que deseas consultar
            userViewModel.consultarUsuario(id) { user ->
                user?.let {
                    Log.d("User", it.toString()) // Muestra el usuario encontrado
                } ?: run {
                    Log.d("User", "Usuario no encontrado") // Maneja el caso en que no se encuentra el usuario
                }
            }

        }

    }

}
