package com.example.navegacion

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private var inicio_de_sesion: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Inicio())
                .commit()
        }


        if (savedInstanceState == null) {
            checkUserSession()
        }
    }
    override fun onStart() {
        super.onStart()
        checkUserSession() // Verificar la sesión cada vez que se inicie la actividad
    }
    private fun checkUserSession() {
        val sharedPreferences = getSharedPreferences("user_prefs", 0)
        val userEmail = sharedPreferences.getString("user_email", null)

        if (userEmail != null) {
            Log.d("MainActivity", "Sesión ya iniciada con: $userEmail")
            goToMainFragment() // Redirigir al fragmento principal
        } else {
            goToLoginFragment() // Redirigir al login si no hay sesión activa
        }
    }

    private fun goToMainFragment() {
        inicio_de_sesion = true
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.nav_item_seven)
        loginItem?.title = "Logout"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, inicio2())
            .commit()
    }

    private fun goToLoginFragment() {
        inicio_de_sesion=false
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.nav_item_seven)
        loginItem?.title = "Login"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Inicio())
            .commit()
    }
    fun updateMenuTitle(newTitle: String) {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val menu = navigationView.menu
        val loginItem = menu.findItem(R.id.nav_item_seven)
        loginItem.title = newTitle // Cambia el título del ítem
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment = when (item.itemId) {
            R.id.nav_item_seven -> Item1Fragment()
            R.id.nav_item_two -> Item2Fragment()
            R.id.nav_item_three -> Item3Fragment()
            R.id.nav_item_six -> configuracion()
            else -> if (inicio_de_sesion) inicio2() else Inicio()
        }

        if(fragment !is Item1Fragment && fragment !is configuracion && !inicio_de_sesion){
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,Item1Fragment())
                .addToBackStack(null).commit()
            drawer.closeDrawer(GravityCompat.START)
            return true
        }

        if (fragment is Item1Fragment && inicio_de_sesion) {
            // Limpiar SharedPreferences para cerrar la sesión
            val sharedPreferences = getSharedPreferences("user_prefs", 0)
            with(sharedPreferences.edit()) {
                clear() // Borra todos los datos guardados
                apply()
            }

            // Mostrar mensaje de cierre de sesión
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

            // Cambiar el título del menú a "Login"
            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            val menu = navigationView.menu
            val loginItem = menu.findItem(R.id.nav_item_seven)
            loginItem.title = "Login" // Actualiza el título

            // Redirigir al fragmento de inicio de sesión
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Inicio())
                .commit()
            inicio_de_sesion=false
            drawer.closeDrawer(GravityCompat.START)
            return true // Detenemos aquí para evitar re-carga del fragmento
        }

        // Si no es el Item1Fragment, continuar con la navegación normal
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
