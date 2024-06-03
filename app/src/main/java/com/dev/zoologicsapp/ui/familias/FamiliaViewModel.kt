package com.dev.zoologicsapp.ui.familias

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FamiliaViewModel : ViewModel() {
    private val _familiaCreationStatus = MutableLiveData<Boolean>()
    val familiaCreationStatus: LiveData<Boolean> get() = _familiaCreationStatus

    private val _familias = MutableLiveData<List<Familia>>()
    val familias: LiveData<List<Familia>> get() = _familias

    private val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun crearFamilia(familiaCreada: String) {
        val fam = hashMapOf(
            "Familia" to familiaCreada
        )

        mFirestore.collection("Familias").add(fam).addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            _familiaCreationStatus.value = true
        }.addOnFailureListener {
            _familiaCreationStatus.value = false
        }
    }

    fun fetchFamilias() {
        mFirestore.collection("Familias")
            .get()
            .addOnSuccessListener { documents ->
                val familiaList = documents.map { document ->
                    Familia(
                        id = document.id,
                        familia = document.getString("Familia") ?: ""
                    )
                }
                _familias.value = familiaList
            }
            .addOnFailureListener { exception ->
                Log.e("FamiliaViewModel", "Error fetching families", exception)
            }
    }

    fun updateFamilia(familia: Familia, newFamiliaName: String) {
        val updatedData = hashMapOf(
            "Familia" to newFamiliaName
        )

        mFirestore.collection("Familias").document(familia.id)
            .set(updatedData, SetOptions.merge())
            .addOnSuccessListener {
                fetchFamilias() // Refresh the list after update
                Log.d(ContentValues.TAG, "La familia fue modificada correctamente")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error al modificar", e)
            }
    }

}