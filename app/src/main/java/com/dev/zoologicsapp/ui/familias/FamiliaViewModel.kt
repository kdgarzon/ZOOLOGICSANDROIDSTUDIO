package com.dev.zoologicsapp.ui.familias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FamiliaViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento de familias"
    }
    val text: LiveData<String> = _text
}