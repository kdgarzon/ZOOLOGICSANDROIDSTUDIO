package com.dev.zoologicsapp.ui.VistaUsuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZooSingapurViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento del zoologico de singapur"
    }
    val text: LiveData<String> = _text
}