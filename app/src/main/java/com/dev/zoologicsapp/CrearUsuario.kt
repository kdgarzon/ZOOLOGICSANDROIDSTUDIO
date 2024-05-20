package com.dev.zoologicsapp

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearUsuario : AppCompatActivity() {

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_usuario)

        autoCompleteTextView = findViewById(R.id.autoCompleteList)
        fetchRolesFromFirestore()


    }

    private fun fetchRolesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val roleList = ArrayList<String>()

        db.collection("roles")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val rol = document.getString("rol")
                    rol?.let { roleList.add(it) }
                }

                adapterItems = ArrayAdapter(this, R.layout.lista_roles, roleList)
                autoCompleteTextView.setAdapter(adapterItems)
                autoCompleteTextView.setOnItemClickListener(AdapterView.OnItemClickListener{ parent, view, position, id ->
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    val message = "Item seleccionado: $selectedItem"
                    Toast.makeText(this@CrearUsuario, message, Toast.LENGTH_SHORT).show()
                })
            }
            .addOnFailureListener { exception ->
                println("Error al traer los elementos de la colección: $exception")
            }
    }
}