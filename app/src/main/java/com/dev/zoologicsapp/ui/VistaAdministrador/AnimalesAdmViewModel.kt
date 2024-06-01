package com.dev.zoologicsapp.ui.VistaAdministrador

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnimalesAdmViewModel : ViewModel() {
    private val _especies = MutableLiveData<List<String>>()
    val especies: LiveData<List<String>> get() = _especies

    private val _sexo = MutableLiveData<List<String>>()
    val sexo: LiveData<List<String>> get() = _sexo

    private val _continentes = MutableLiveData<List<String>>()
    val continentes: LiveData<List<String>> get() = _continentes

    private val _paises = MutableLiveData<List<String>>()
    val paises: LiveData<List<String>> get() = _paises

    private val _zoologicos = MutableLiveData<List<String>>()
    val zoologicos: LiveData<List<String>> get() = _zoologicos

    private val _animales = MutableLiveData<List<Animal>>()
    val animales: LiveData<List<Animal>> get() = _animales

    val selectedEspecie = MutableLiveData<String>()
    val selectedSex = MutableLiveData<String>()
    val selectedContinente = MutableLiveData<String>()
    val selectedPais = MutableLiveData<String>()
    val selectedZoo = MutableLiveData<String>()


    private val _animalCreationStatus = MutableLiveData<Boolean>()
    val animalCreationStatus: LiveData<Boolean> get() = _animalCreationStatus

    private val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchEspeciesFromFirestore()
        fetchSexoFromFirestore()
        fetchContinentesFromFirestore()
        fetchPaisesFromFirestore()
        fetchZoologicosFromFirestore()
    }

    fun fetchEspeciesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val especiesList = ArrayList<String>()

        db.collection("Especies")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val esp = document.getString("Nombre_vulgar")
                    esp?.let { especiesList.add(it) }
                }
                especiesList.sort()
                _especies.value = especiesList
            }
            .addOnFailureListener { exception ->
                Log.e("AnimalesAdmViewModel", "Error al traer los elementos de la colección de especies: $exception")
            }
    }

    fun fetchSexoFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val sexoList = ArrayList<String>()

        db.collection("Sexo")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val se = document.getString("Sexo")
                    se?.let { sexoList.add(it) }
                }
                sexoList.sort()
                _sexo.value = sexoList
            }
            .addOnFailureListener { exception ->
                Log.e("AnimalesAdmViewModel", "Error al traer los elementos de la colección de sexo: $exception")
            }
    }

    fun fetchContinentesFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val contList = ArrayList<String>()

        db.collection("Continentes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val continente = document.getString("Continente")
                    continente?.let { contList.add(it) }
                }
                contList.sort()
                _continentes.value = contList
            }
            .addOnFailureListener { exception ->
                Log.e("AnimalesAdmViewModel", "Error al traer los elementos de la colección de continentes: $exception")
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
                Log.e("AnimalesAdmViewModel", "Error al traer los elementos de la colección de paises: $exception")
            }
    }

    fun fetchZoologicosFromFirestore() {
        val db: FirebaseFirestore = Firebase.firestore
        val zooList = ArrayList<String>()

        db.collection("Zoologicos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val zoo = document.getString("NombreZoo")
                    zoo?.let { zooList.add(it) }
                }
                zooList.sort()
                _zoologicos.value = zooList
            }
            .addOnFailureListener { exception ->
                Log.e("AnimalesAdmViewModel", "Error al traer los elementos de la colección de zoologicos: $exception")
            }
    }

    fun fetchAnimales() {
        mFirestore.collection("Animales")
            .get()
            .addOnSuccessListener { documents ->
                val animalList = mutableListOf<Animal>()
                for (document in documents) {
                    val animal = document.toObject(Animal::class.java)
                    animalList.add(animal)
                }
                _animales.value = animalList
            }
            .addOnFailureListener { exception ->
                Log.e("AnimalesAdmViewModel", "Error al traer los animales: $exception")
            }
    }

    fun crearAnimal(anioCreado: Number, nomCreado: String) {
        val animal = hashMapOf(
            "Nombre_animal" to nomCreado,
            "Especie" to selectedEspecie.value,
            "Continente" to selectedContinente.value,
            "Año" to anioCreado,
            "Sexo" to selectedSex.value,
            "Zoo_Pertenece" to selectedZoo.value,
            "Pais" to selectedPais.value
        )
        mFirestore.collection("Animales").add(animal).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            _animalCreationStatus.value = true
        }.addOnFailureListener {
            _animalCreationStatus.value = false
        }
    }
}