package com.dev.zoologicsapp.ui.VistaUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentZooBronxBinding

class ZooBronx : Fragment() {

    private var _binding: FragmentZooBronxBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bronxViewModel =
            ViewModelProvider(this)[ZooBronxViewModel::class.java]

        _binding = FragmentZooBronxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textZooBronx
        bronxViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}