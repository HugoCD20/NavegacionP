package com.example.navegacion

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels

class Registro : Fragment(R.layout.fragment_registro) {
    private val userViewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username: EditText = view.findViewById(R.id.username)
        val email: EditText = view.findViewById(R.id.email)
        val password: EditText = view.findViewById(R.id.contraseña1)
        val confirmPassword: EditText = view.findViewById(R.id.contraseña2)
        val registerbutton: Button = view.findViewById(R.id.boton5)

        registerbutton.setOnClickListener {
            val username2 = username.text.toString()
            val emailEditText = email.text.toString().trim()
            val passwordEditText = password.text.toString()
            val confirmPasswordEditText = confirmPassword.text.toString()

            if (validateInput(emailEditText, passwordEditText, confirmPasswordEditText)) {
                val user = User(username = username2, email = emailEditText, password = passwordEditText)

                userViewModel.insertarUsuario(user) { isSuccessful, message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    if (isSuccessful) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, Item1Fragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }


    }
    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty()) {
            showToast("El correo no puede estar vacío")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("El correo no tiene un formato válido")
            return false
        }
        if (password.length < 6) {
            showToast("La contraseña debe tener al menos 6 caracteres")
            return false
        }
        if (password != confirmPassword) {
            showToast("Las contraseñas no coinciden")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}