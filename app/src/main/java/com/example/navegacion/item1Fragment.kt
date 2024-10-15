package com.example.navegacion

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels


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
        val redireccion: TextView = view.findViewById(R.id.textView6)
        redireccion.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Registro())
                .addToBackStack(null)
                .commit()
        }

    }

}
