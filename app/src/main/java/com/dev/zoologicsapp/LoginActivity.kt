package com.dev.zoologicsapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var btnIniciarSesion: Button
    private lateinit var btnReg: Button
    private lateinit var editEmail: EditText
    private lateinit var editPass: EditText
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        this.setTitle("INICIAR SESION")

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnReg = findViewById(R.id.btnRegistrarse)
        editEmail = findViewById(R.id.editTextEmailIniciar)
        editPass = findViewById(R.id.editTextPassIniciar)

        btnIniciarSesion.setOnClickListener{
            val emailUser = editEmail.text.toString().trim()
            val passUser = editPass.text.toString().trim()

            if (emailUser.isEmpty() && passUser.isEmpty()){
                Toast.makeText(this@LoginActivity, "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            }else{
                loginUser(emailUser, passUser)
            }
        }

        btnReg.setOnClickListener{
            irRegistrar()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUser(emailUser: String, passUser: String) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = mAuth.currentUser
                user?.let {
                    obtenerRol(it.uid)
                }
            }else{
                Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this@LoginActivity, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerRol(userID: String) {
        mFirestore.collection("Usuarios")
            .whereEqualTo("id", userID)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first() // Tomamos el primer documento que coincida con el id
                    val role = document.getString("Rol")
                    if (role != null) {
                        dirigirPaneles(role)
                    } else {
                        Toast.makeText(this@LoginActivity, "No se encontró el rol del usuario", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si no se encuentra el documento del usuario
                    Toast.makeText(this@LoginActivity, "No se encontró el documento del usuario", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                // Si ocurre un error durante la consulta
                Toast.makeText(this@LoginActivity, "Error al obtener el rol del usuario", Toast.LENGTH_SHORT).show()
            }
    }

    private fun dirigirPaneles(role: String) {
        when (role) {
            "Usuario" -> {
                startActivity(Intent(this@LoginActivity, PanelUsuario::class.java))
            }
            "Administrador" -> {
                startActivity(Intent(this@LoginActivity, PanelAdministrador::class.java))
            }
            else -> {
                startActivity(Intent(this@LoginActivity, PanelZoo::class.java))
            }
        }
        finish()
    }

    private fun irRegistrar() {
        startActivity(Intent(this@LoginActivity, RegistroActivity::class.java))
        finish()
    }
}