package com.example.navegacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner


class Registro_task_firs : Fragment(R.layout.fragment_registro_task_firs) {
    private lateinit var seleccion: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.spinner)
        val opciones = listOf("Escuela", "Trabajo", "Deporte")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val registroTaskSecond =Registro_task_second();
        val bundle = Bundle();


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                 seleccion = opciones[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acciones cuando no se selecciona nada (opcional)
            }
        }
        val boton: ImageButton = view.findViewById(R.id.boton_categoria)
       boton.setOnClickListener {
            bundle.putString("categoria",seleccion)
            registroTaskSecond.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,registroTaskSecond)
                .addToBackStack(null).commit()
        }
    }

}