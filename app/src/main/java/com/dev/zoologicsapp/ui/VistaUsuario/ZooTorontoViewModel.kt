package com.dev.zoologicsapp.ui.VistaUsuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZooTorontoViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento del zoológico de Toronto"
    }
    val text: LiveData<String> = _text
}