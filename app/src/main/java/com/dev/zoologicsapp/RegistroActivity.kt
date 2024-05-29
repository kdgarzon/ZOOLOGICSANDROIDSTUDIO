package com.dev.zoologicsapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private lateinit var btnRegistrarUsuario: Button
    private lateinit var btnIniciar: Button
    private lateinit var editIdentificacion: EditText
    private lateinit var editNombre: EditText
    private lateinit var editApellido: EditText
    private lateinit var editCorreo: EditText
    private lateinit var editUser: EditText
    private lateinit var editPass: EditText
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        this.setTitle("REGISTRAR USUARIO")

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        btnRegistrarUsuario = findViewById(R.id.btnRegistrar)
        btnIniciar = findViewById(R.id.btnIngresar)
        editIdentificacion = findViewById(R.id.editTextIdent)
        editNombre = findViewById(R.id.editTextNombre)
        editApellido = findViewById(R.id.editTextApellido)
        editCorreo = findViewById(R.id.editTextCorreo)
        editUser = findViewById(R.id.editTextUsuario)
        editPass = findViewById(R.id.editTextContraseña)

        btnRegistrarUsuario.setOnClickListener{
            val identUser = editIdentificacion.text.toString().trim()
            val nomUser = editNombre.text.toString().trim()
            val apellUser = editApellido.text.toString().trim()
            val correoUser = editCorreo.text.toString().trim()
            val userUser = editUser.text.toString().trim()
            val passUser = editPass.text.toString().trim()

            //Se comprueba que los datos no esten vacios
            if (identUser.isEmpty() && nomUser.isEmpty() && apellUser.isEmpty() && correoUser.isEmpty() && userUser.isEmpty() && passUser.isEmpty()){
                Toast.makeText(this@RegistroActivity, "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            }else{
                registrar(identUser, nomUser, apellUser, correoUser, userUser, passUser)
            }
        }

        btnIniciar.setOnClickListener{
            iniciarSesion()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun iniciarSesion() {
        startActivity(Intent(this@RegistroActivity, LoginActivity::class.java))
        finish()
    }


    //METODO PARA REGISTRAR USUARIOS
    private fun registrar(identUser: String, nomUser: String, apellUser: String, correoUser: String, userUser: String, passUser: String) {

        mAuth.createUserWithEmailAndPassword(correoUser, passUser).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val currentUser = mAuth.currentUser
                val rol = "Usuario"
                if (currentUser != null) {
                    val id = currentUser.uid

                    val user = hashMapOf(
                        "id" to id,
                        "Identificacion" to identUser,
                        "Nombre" to nomUser,
                        "Apellido" to apellUser,
                        "Correo" to correoUser,
                        "Username" to userUser,
                        "Contraseña" to passUser,
                        "Rol" to rol
                    )

                    mFirestore.collection("Usuarios").add(user).addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        finish()
                        startActivity(Intent(this@RegistroActivity, LoginActivity::class.java))
                        Toast.makeText(this@RegistroActivity, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@RegistroActivity, "No se creó el usuario en la colección Usuarios", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegistroActivity, "Ningún usuario ha iniciado sesión", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@RegistroActivity, "Error, no se pudo crear el usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }


}