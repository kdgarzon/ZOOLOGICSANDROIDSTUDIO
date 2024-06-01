package com.dev.zoologicsapp.ui.VistaAdministrador

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EspeciesAdmViewModel : ViewModel() {
    private val _familias = MutableLiveData<List<String>>()
    val familias: LiveData<List<String>> get() = _familias

    private val _estados = MutableLiveData<List<String>>()
    val estados: LiveData<List<String>> get() = _estados

    val selectedFamilia = MutableLiveData<String>()
    val selectedEstado = MutableLiveData<String>()

    private val _especieCreationStatus = MutableLiveData<Boolean>()
    val especieCreationStatus: LiveData<Boolean> get() = _especieCreationStatus

    private val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchFamiliasFromFirestore()
        fetchEstadosFromFirestore()
    }

    fun fetchFamiliasFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val familiaList = ArrayList<String>()

        db.collection("Familias")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val fami = document.getString("Familia")
                    fami?.let { familiaList.add(it) }
                }
                familiaList.sort()
                _familias.value = familiaList
            }
            .addOnFailureListener { exception ->
                Log.e("EspeciesAdmViewModel", "Error al traer los elementos de la colección de familias: $exception")
            }
    }

    fun fetchEstadosFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val estadosList = ArrayList<String>()

        db.collection("Extincion")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val estado = document.getString("Nivel_Peligro")
                    estado?.let { estadosList.add(it) }
                }
                estadosList.sort()
                _estados.value = estadosList
            }
            .addOnFailureListener { exception ->
                Log.e("EspeciesAdmViewModel", "Error al traer los elementos de la colección de extincion: $exception")
            }
    }



    fun crearEspecie(nomVulgarCreado: String, nomCientificoCreado: String) {
        val espe = hashMapOf(
            "Nombre_vulgar" to nomVulgarCreado,
            "Nombre_Cientifico" to nomCientificoCreado,
            "Familia" to selectedFamilia.value,
            "Nivel_Peligro" to selectedEstado.value
        )
        mFirestore.collection("Especies").add(espe).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            _especieCreationStatus.value = true
        }.addOnFailureListener {
            _especieCreationStatus.value = false
        }
    }

}