package com.dev.zoologicsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var btnCerrar: Button
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        this.setTitle("PAGINA PRINCIPAL")

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        btnCerrar = findViewById(R.id.btnCerrarSesion)

        btnCerrar.setOnClickListener{
            cerrar()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cerrar() {
        mAuth.signOut()
        finish()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            Toast.makeText(this@MainActivity, "Ya tienes una sesión iniciada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Inicia sesión", Toast.LENGTH_SHORT).show()
        }
    }
}