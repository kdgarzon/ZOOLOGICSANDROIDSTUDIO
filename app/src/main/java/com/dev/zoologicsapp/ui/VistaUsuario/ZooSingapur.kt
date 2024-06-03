package com.dev.zoologicsapp.ui.VistaUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentZooSingapurBinding

class ZooSingapur : Fragment() {

    private var _binding: FragmentZooSingapurBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val singapurViewModel =
            ViewModelProvider(this)[ZooSingapurViewModel::class.java]

        _binding = FragmentZooSingapurBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}