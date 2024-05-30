package com.dev.zoologicsapp.ui.VistaAdministrador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentAnimalesAdmBinding

class AnimalesAdm : Fragment() {

    private var _binding: FragmentAnimalesAdmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val animalesAdmViewModel =
            ViewModelProvider(this)[AnimalesAdmViewModel::class.java]

        _binding = FragmentAnimalesAdmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAnimalesAdm
        animalesAdmViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}