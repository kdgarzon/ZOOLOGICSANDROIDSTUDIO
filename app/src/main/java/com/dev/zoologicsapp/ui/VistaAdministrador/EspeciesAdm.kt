package com.dev.zoologicsapp.ui.VistaAdministrador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentEspeciesAdmBinding

class EspeciesAdm : Fragment() {

    private var _binding: FragmentEspeciesAdmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val especiesAdmViewModel =
            ViewModelProvider(this)[EspeciesAdmViewModel::class.java]

        _binding = FragmentEspeciesAdmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEspeciesAdm
        especiesAdmViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}