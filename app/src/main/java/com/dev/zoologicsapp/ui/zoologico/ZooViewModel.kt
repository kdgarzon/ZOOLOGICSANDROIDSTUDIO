package com.dev.zoologicsapp.ui.zoologico

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZooViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento de zoologico"
    }
    val text: LiveData<String> = _text
}