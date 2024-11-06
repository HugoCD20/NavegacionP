package com.example.navegacion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.setFragmentResultListener
import android.widget.Toast
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels

class Registro_task_second : Fragment(R.layout.fragment_registro_task_second) {
    private lateinit var fecha:String
    private lateinit var hora:String
    private lateinit var seleccion:String
    private lateinit var mensaje:String
    private  var user_id:Int = 0
    private val TaskViewModel: TaskViewModel by viewModels()
    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", 0)
         user_id = sharedPreferences.getInt("user_id",0)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.spinner)
        val opciones = listOf("Baja", "Media", "Alta")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
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

         mensaje = arguments?.getString("categoria").toString()

        val boton: ImageButton = view.findViewById(R.id.pickTime)
        val boton2: ImageButton = view.findViewById(R.id.agregar_fecha)
        val contenH: EditText = view.findViewById(R.id.hora_content)
        val contenD: EditText = view.findViewById(R.id.agregar_dia)
        val agregar_tarea: Button = view.findViewById(R.id.agregar_tarea)
        val agregar_titulo: EditText = view.findViewById(R.id.agregar_titulo)
        val agregar_descripcion: EditText = view.findViewById(R.id.agregar_descripcion)

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
            hora= String.format("%02d:%02d", hour, minute)
        }
        setFragmentResultListener("datePickerKey") { _, bundle ->
            val año = bundle.getInt("año")
            val mes = bundle.getInt("mes")
            val dia = bundle.getInt("dia")
            contenD.setText(String.format("%02d/%02d/%04d", dia, mes, año))
            fecha=String.format("%02d/%02d/%04d", dia, mes, año)
        }

        agregar_tarea.setOnClickListener {
            val text_titulo= agregar_titulo.text.toString()
            val text_descripcion= agregar_descripcion.text.toString()

            if(validateData(text_titulo,fecha,hora,seleccion,text_descripcion)){
                try {
                    TaskViewModel.insertTask(Task(fk_user=user_id, title = text_titulo, description = text_descripcion,
                        date = fecha, hour = hora, priority = seleccion, status = "Iniciado", category = mensaje))
                    mensaje("Tarea registrada correctamente")

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,inicio2())
                        .commit()
                } catch (e:Exception){
                    mensaje("Error al intentar agregar una tarea")
                }

            }
        }

    }
    private fun validateData(Titulo:String,Dia:String,Hora:String,Prioridad:String,Descripcion:String): Boolean {

        if(Titulo.isEmpty()){
            mensaje("Coloca un titulo")
            return false
        }
        if(Dia.isEmpty()){
            mensaje("Coloca un Dia")
            return false
        }
        if(Hora.isEmpty()){
            mensaje("Coloca una hora")
            return false
        }
        if(Prioridad.isEmpty()){
            mensaje("Elige una prioridad")
            return false
        }
        if(Descripcion.isEmpty()){
            mensaje("Escribe una descripción para la tarea")
            return false
        }
        return true
    }
    private fun mensaje(msg:String){
        Toast.makeText(requireContext(),msg,Toast.LENGTH_SHORT).show()
    }
}

