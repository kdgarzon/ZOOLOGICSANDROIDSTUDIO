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
import com.dev.zoologicsapp.databinding.ActivityPanelZooBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PanelZoo : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPanelZooBinding
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var headerView: View
    private lateinit var mAuth: FirebaseAuth
    private lateinit var textViewRole: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPanelZooBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPanelZoo.toolbar)

        binding.appBarPanelZoo.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_panel_zoo)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_infoZoo, R.id.nav_animaleszoo,
                R.id.nav_especieszoo, R.id.nav_zoologico, R.id.nav_familiaszoo
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // Obtener el rol y colocarlo en la barra de navegación dependiendo de quien inicie sesion
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()

        // Configurar el encabezado del NavigationView
        headerView = navView.getHeaderView(0)
        textViewRole = headerView.findViewById(R.id.textView)

        // Obtener el rol del usuario y actualizar el TextView

        val user = mAuth.currentUser
        user?.let {
            mostrarRol(it.uid)
        }
    }

    private fun mostrarRol(uid: String) {
        mFirestore.collection("Usuarios")
            .whereEqualTo("id", uid)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first() // Tomamos el primer documento que coincida con el id
                    val role = document.getString("Rol")
                    if (role != null) {
                        textViewRole.text = role
                    } else {
                        Toast.makeText(this@PanelZoo, "No se encontró el rol del usuario", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si no se encuentra el documento del usuario
                    Toast.makeText(this@PanelZoo, "No se encontró el documento del usuario", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                // Si ocurre un error durante la consulta
                Toast.makeText(this@PanelZoo, "Error al obtener el rol del usuario", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.panel_zoo, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_panel_zoo)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}