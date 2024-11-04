package com.example.navegacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


class inicio2 : Fragment(R.layout.fragment_inicio2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boton1: ImageButton = view.findViewById(R.id.Agregar_tarea)

        boton1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Registro_task_firs())
                .addToBackStack(null).commit()
        }
    }

}