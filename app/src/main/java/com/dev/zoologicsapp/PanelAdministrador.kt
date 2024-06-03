package com.dev.zoologicsapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.zoologicsapp.databinding.ActivityPanelAdministradorBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PanelAdministrador : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPanelAdministradorBinding
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var headerView: View
    private lateinit var mAuth: FirebaseAuth
    private lateinit var textViewRole: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPanelAdministradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPanelAdministrador.toolbar)

        /*binding.appBarPanelAdministrador.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_panel_administrador)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home_administrador, R.id.nav_infoAdmin, R.id.nav_usuariosAdmin,
                R.id.nav_zoologicosAdmin, R.id.nav_animalesAdmin, R.id.nav_especiesAdmin
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Obtener el user y colocarlo en la barra de navegación dependiendo de quien inicie sesion
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()

        // Configurar el encabezado del NavigationView
        headerView = navView.getHeaderView(0)
        textViewRole = headerView.findViewById(R.id.textView)

        // Obtener el user del usuario y actualizar el TextView

        val user = mAuth.currentUser
        user?.let {
            mostrarUser(it.uid)
        }

    }

    private fun mostrarUser(uid: String) {
        mFirestore.collection("Usuarios")
            .whereEqualTo("id", uid)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first() // Tomamos el primer documento que coincida con el id
                    val usern= document.getString("username")
                    if (usern != null) {
                        textViewRole.text = usern
                    } else {
                        Toast.makeText(this@PanelAdministrador, "No se encontró el user del usuario", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si no se encuentra el documento del usuario
                    Toast.makeText(this@PanelAdministrador, "No se encontró el documento del usuario", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                // Si ocurre un error durante la consulta
                Toast.makeText(this@PanelAdministrador, "Error al obtener el user del usuario", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.panel_administrador, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_panel_administrador)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}