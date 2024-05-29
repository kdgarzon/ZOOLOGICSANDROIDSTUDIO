package com.dev.zoologicsapp.ui.VistaUsuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZooLondresViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento del zoologico de Londres"
    }
    val text: LiveData<String> = _text
}