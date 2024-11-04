package com.example.navegacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.setFragmentResultListener
import android.widget.Toast

class Registro_task_second : Fragment(R.layout.fragment_registro_task_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.spinner)
        val opciones = listOf("Baja", "Media", "Alta")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val mensaje = arguments?.getString("categoria")
        mensaje?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        val boton: ImageButton = view.findViewById(R.id.pickTime)
        val boton2: ImageButton = view.findViewById(R.id.agregar_fecha)
        val contenH: EditText = view.findViewById(R.id.hora_content)
        val contenD: EditText = view.findViewById(R.id.agregar_dia)
        val agregar_tarea: Button = view.findViewById(R.id.agregar_tarea)

        boton.setOnClickListener {
            TimePickerFragment().show(parentFragmentManager, "timePicker")
        }

        boton2.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(parentFragmentManager, "datePicker")
        }

        // Escucha los resultados de timePickerKey y actualiza el EditText con la hora seleccionada
        setFragmentResultListener("timePickerKey") { _, bundle ->
            val hour = bundle.getInt("hour")
            val minute = bundle.getInt("minute")
            contenH.setText(String.format("%02d:%02d", hour, minute))
        }
        setFragmentResultListener("datePickerKey") { _, bundle ->
            val año = bundle.getInt("año")
            val mes = bundle.getInt("mes")
            val dia = bundle.getInt("dia")
            contenD.setText(String.format("%02d/%02d/%04d", dia, mes, año))
        }


    }
}

