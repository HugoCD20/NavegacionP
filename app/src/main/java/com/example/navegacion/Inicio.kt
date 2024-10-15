package com.example.navegacion

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels

class Inicio : Fragment(R.layout.fragment_inicio) {
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
        }

    }
}
