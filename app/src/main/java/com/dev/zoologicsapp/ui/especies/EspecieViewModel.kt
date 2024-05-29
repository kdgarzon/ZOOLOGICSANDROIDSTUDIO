package com.dev.zoologicsapp.ui.especies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EspecieViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento de especies"
    }
    val text: LiveData<String> = _text
}