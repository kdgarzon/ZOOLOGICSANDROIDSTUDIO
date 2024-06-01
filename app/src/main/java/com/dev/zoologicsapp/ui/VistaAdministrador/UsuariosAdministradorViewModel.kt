package com.dev.zoologicsapp.ui.VistaAdministrador

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UsuariosAdministradorViewModel : ViewModel() {
    private val _roles = MutableLiveData<List<String>>()
    val roles: LiveData<List<String>> get() = _roles

    val selectedRole = MutableLiveData<String>()

    private val _userCreationStatus = MutableLiveData<Boolean>()
    val userCreationStatus: LiveData<Boolean> get() = _userCreationStatus

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    private val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        fetchRolesFromFirestore()
    }

    fun fetchRolesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val roleList = ArrayList<String>()

        db.collection("Roles")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val rol = document.getString("Rol")
                    rol?.let { roleList.add(it) }
                }
                _roles.value = roleList
            }
            .addOnFailureListener { exception ->
                Log.e("UsuariosAdmViewModel", "Error al traer los elementos de la colección: $exception")
            }
    }

    fun fetchUsuarios() {
        mFirestore.collection("Usuarios")
            .get()
            .addOnSuccessListener { documents ->
                val userList = mutableListOf<Usuario>()
                for (document in documents) {
                    val usuario = document.toObject(Usuario::class.java)
                    userList.add(usuario)
                }
                _usuarios.value = userList
            }
            .addOnFailureListener { exception ->
                Log.e("UsuariosAdmViewModel", "Error al traer los usuarios: $exception")
            }
    }

    fun crearUsuario(identCreada: String, nomCreado: String, apelCreado: String, userCreado: String, contraCreada: String, correoCreado: String) {
        mAuth.createUserWithEmailAndPassword(correoCreado, contraCreada).addOnCompleteListener { task ->
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
                        "Rol" to selectedRole.value
                    )

                    mFirestore.collection("Usuarios").add(user).addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        _userCreationStatus.value = true
                    }.addOnFailureListener {
                        _userCreationStatus.value = false
                    }
                } else {
                    _userCreationStatus.value = false
                }
            } else {
                _userCreationStatus.value = false
            }
        }
    }
}