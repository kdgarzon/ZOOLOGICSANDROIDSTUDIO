package com.dev.zoologicsapp.ui.informacionZoo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoZooViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento de informacion del zoologico"
    }
    val text: LiveData<String> = _text
}