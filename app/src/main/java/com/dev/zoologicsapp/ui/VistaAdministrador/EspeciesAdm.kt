package com.dev.zoologicsapp.ui.VistaAdministrador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dev.zoologicsapp.R

class EspeciesAdm : Fragment() {

    companion object {
        fun newInstance() = EspeciesAdm()
    }

    private val viewModel: EspeciesAdmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_especies_adm, container, false)
    }
}