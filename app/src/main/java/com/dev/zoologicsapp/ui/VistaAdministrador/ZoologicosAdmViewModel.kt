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



    fun crearZoo(tamCreado: Number, nomCreado: String, presupuestoCreado: Number) {
        val zoo = hashMapOf(
            "NombreZoo" to nomCreado,
            "TamañoMetrosCuadrados" to tamCreado,
            "Presupuesto" to presupuestoCreado,
            "Ciudad" to selectedCiudad.value,
            "Pais" to selectedPais.value
        )
        mFirestore.collection("Zoologicos").add(zoo).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            _zooCreationStatus.value = true
        }.addOnFailureListener {
            _zooCreationStatus.value = false
        }
    }
}