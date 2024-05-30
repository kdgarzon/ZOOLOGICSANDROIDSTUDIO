package com.dev.zoologicsapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearUsuario : AppCompatActivity() {

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var creIdentificacion: EditText
    private lateinit var creNombre: EditText
    private lateinit var creApellido: EditText
    private lateinit var creUsuario: EditText
    private lateinit var creContra: EditText
    private lateinit var creCorreo: EditText
    private lateinit var btnInsertarNuevoUsuario: Button
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var selectedItem: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_usuario)

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        creIdentificacion = findViewById(R.id.editTextNumIdent)
        creNombre = findViewById(R.id.editTextNombre)
        creApellido = findViewById(R.id.editTextApellido)
        creUsuario = findViewById(R.id.editTextUsername)
        creContra = findViewById(R.id.editTextContra)
        creCorreo = findViewById(R.id.editTextEmail)
        btnInsertarNuevoUsuario = findViewById(R.id.btnCrear)

        autoCompleteTextView = findViewById(R.id.autoCompleteList)
        fetchRolesFromFirestore()

        btnInsertarNuevoUsuario.setOnClickListener{
            val identCreada = creIdentificacion.text.toString().trim()
            val nomCreado = creNombre.text.toString().trim()
            val apelCreado = creApellido.text.toString().trim()
            val userCreado = creUsuario.text.toString().trim()
            val contraCreada = creContra.text.toString().trim()
            val correoCreado = creCorreo.text.toString().trim()

            if (identCreada.isEmpty() || nomCreado.isEmpty() || apelCreado.isEmpty() || userCreado.isEmpty() || contraCreada.isEmpty() || correoCreado.isEmpty() || selectedItem.isEmpty()){
                Toast.makeText(this@CrearUsuario, "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            }else{
                crear(identCreada, nomCreado, apelCreado, userCreado, contraCreada, correoCreado, selectedItem)
            }
        }

    }

    private fun crear(identCreada: String, nomCreado: String, apelCreado: String, userCreado: String, contraCreada: String, correoCreado: String, selectedItem: String) {
        mAuth.createUserWithEmailAndPassword(correoCreado, contraCreada).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val currentUser = mAuth.currentUser
                if (currentUser != null) {
                    val id = currentUser.uid

                    val user = hashMapOf(
                        "id" to id,
                        "Identificacion" to identCreada,
                        "Nombre" to nomCreado,
                        "Apellido" to apelCreado,
                        "Correo" to correoCreado,
                        "Username" to userCreado,
                        "Contraseña" to contraCreada,
                        "Rol" to selectedItem
                    )

                    mFirestore.collection("Usuarios").add(user).addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        finish()
                        //startActivity(Intent(this@CrearUsuario, MainActivity::class.java))
                        Toast.makeText(this@CrearUsuario, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@CrearUsuario, "No se creó el usuario en la colección Usuarios", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CrearUsuario, "Ningún usuario ha iniciado sesión", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@CrearUsuario, "Error, no se pudo crear el usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun fetchRolesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val roleList = ArrayList<String>()

        db.collection("Roles")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val rol = document.getString("Rol")
                    rol?.let { roleList.add(it) }
                }

                adapterItems = ArrayAdapter(this, R.layout.lista_roles, roleList)
                autoCompleteTextView.setAdapter(adapterItems)
                autoCompleteTextView.onItemClickListener =
                    AdapterView.OnItemClickListener{ parent, view, position, id ->
                        selectedItem = parent.getItemAtPosition(position).toString()
                        val message = "Item seleccionado: $selectedItem"
                        Toast.makeText(this@CrearUsuario, message, Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                println("Error al traer los elementos de la colección: $exception")
            }
    }
}