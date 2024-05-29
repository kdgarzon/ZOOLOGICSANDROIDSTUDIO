package com.dev.zoologicsapp.ui.VistaAdministrador

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsuariosAdministradorViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento Usuarios Administrador"
    }
    val text: LiveData<String> = _text
}