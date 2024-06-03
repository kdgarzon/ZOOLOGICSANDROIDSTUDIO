package com.dev.zoologicsapp.ui.VistaAdministrador

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ZoologicosAdmViewModel : ViewModel() {
    private val _ciudades = MutableLiveData<List<String>>()
    val ciudades: LiveData<List<String>> get() = _ciudades

    private val _paises = MutableLiveData<List<String>>()
    val paises: LiveData<List<String>> get() = _paises

    val selectedCiudad = MutableLiveData<String>()
    val selectedPais = MutableLiveData<String>()

    private val _zooCreationStatus = MutableLiveData<Boolean>()
    val zooCreationStatus: LiveData<Boolean> get() = _zooCreationStatus

    private val _zoologicos = MutableLiveData<List<Zoologico>>()
    val zoologicos: LiveData<List<Zoologico>> get() = _zoologicos

    private val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchCiudadesFromFirestore()
        fetchPaisesFromFirestore()
    }

    fun fetchCiudadesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val ciudadList = ArrayList<String>()

        db.collection("Ciudad")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val ciudad = document.getString("Ciudad")
                    ciudad?.let { ciudadList.add(it) }
                }
                ciudadList.sort()
                _ciudades.value = ciudadList
            }
            .addOnFailureListener { exception ->
                Log.e("ZoologicosAdmViewModel", "Error al traer los elementos de la colección de ciudades: $exception")
            }
    }

    fun fetchPaisesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val paisList = ArrayList<String>()

        db.collection("Paises")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val pais = document.getString("Pais")
                    pais?.let { paisList.add(it) }
                }
                paisList.sort()
                _paises.value = paisList
            }
            .addOnFailureListener { exception ->
                Log.e("ZoologicosAdmViewModel", "Error al traer los elementos de la colección de paises: $exception")
            }
    }

    fun fetchZoologicos() {
        mFirestore.collection("Zoologicos")
            .get()
            .addOnSuccessListener { documents ->
                val zooList = mutableListOf<Zoologico>()
                for (document in documents) {
                    val zoologico = document.toObject(Zoologico::class.java)
                    zoologico.id = document.id
                    zooList.add(zoologico)
                }
                _zoologicos.value = zooList
            }
            .addOnFailureListener { exception ->
                Log.e("ZoologicosAdmViewModel", "Error al traer los zoologicos: $exception")
            }
    }

    fun crearZoo(tamCreado: Number, nomCreado: String, presupuestoCreado: Number) {
        val zoo = hashMapOf(
            "nombrezoo" to nomCreado,
            "tamañometroscuadrados" to tamCreado,
            "presupuesto" to presupuestoCreado,
            "ciudad" to selectedCiudad.value,
            "pais" to selectedPais.value
        )

        mFirestore.collection("Zoologicos").add(zoo)
            .addOnSuccessListener { documentReference ->
                val zooId = documentReference.id
                // Actualizar el zoologico con el ID del documento
                val zooConId = Zoologico(
                    id = zooId,
                    nombrezoo = nomCreado,
                    tamañometroscuadrados = tamCreado.toInt(),
                    presupuesto = presupuestoCreado.toInt(),
                    pais = selectedPais.value ?: "",
                    ciudad = selectedCiudad.value ?: ""
                )
                // Guardar el zoologico con el ID actualizado en Firestore
                mFirestore.collection("Zoologicos").document(zooId)
                    .set(zooConId)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: $zooId")
                        _zooCreationStatus.value = true
                    }
                    .addOnFailureListener {
                        _zooCreationStatus.value = false
                    }
            }
            .addOnFailureListener {
                _zooCreationStatus.value = false
            }
    }
    fun updatedZoologico(zoologico: Zoologico) {
        mFirestore.collection("Zoologicos").document(zoologico.id)
            .set(zoologico)
            .addOnSuccessListener {
                fetchZoologicos() // Refresh the animal list
                Log.d("ZoologicosAdmViewModel", "Zoologico actualizado correctamente")
            }
            .addOnFailureListener { exception ->
                Log.e("ZoologicosAdmViewModel", "Error al actualizar el zoologico: $exception")
            }
    }
}