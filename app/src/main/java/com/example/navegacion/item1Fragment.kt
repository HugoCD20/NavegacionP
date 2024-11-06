package com.example.navegacion

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.mindrot.jbcrypt.BCrypt

class Item1Fragment : Fragment(R.layout.fragment_item1) {
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boton1: Button = view.findViewById(R.id.botonI)
        val emailE: EditText = view.findViewById(R.id.email2)
        val passwordE: EditText = view.findViewById(R.id.password1)

        // Botón de inicio de sesión
        boton1.setOnClickListener {
            val email = emailE.text.toString()
            val password = passwordE.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password) // Inicia sesión
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirección al fragmento de registro
        val redireccion: TextView = view.findViewById(R.id.textView6)
        redireccion.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Registro())
                .addToBackStack(null)
                .commit()
        }
    }
    private fun restartActivity() {
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }

    // Método para autenticar al usuario usando Room
    private fun loginUser(email: String, password: String) {
        userViewModel.consultarUsuarioPorEmail(email) { user ->
            if (user != null && BCrypt.checkpw(password, user.password)) {
                Log.d("LoginActivity", "Login exitoso")

                // Guardar el correo del usuario en SharedPreferences
                val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", 0)
                with(sharedPreferences.edit()) {
                    putString("user_email", email)
                    putInt("user_id",user.id)
                    apply()
                }
                (activity as? MainActivity)?.updateMenuTitle("Logout")
                restartActivity()
                //goToMainActivity() // Redirige al siguiente fragmento
            } else {
                Log.w("LoginActivity", "Error de autenticación")
                Toast.makeText(requireContext(), "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Navega al siguiente fragmento después del inicio de sesión
    private fun goToMainActivity() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, inicio2())
            .commit()
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", 0)
        val userEmail = sharedPreferences.getString("user_email", null)

        if (userEmail != null) {
            Log.d("LoginActivity", "Sesión ya iniciada con: $userEmail")
            goToMainActivity() // Usuario ya autenticado
        }
    }

}
