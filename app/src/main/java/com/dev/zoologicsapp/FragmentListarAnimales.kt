package com.dev.zoologicsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarAnimalesBinding
import com.dev.zoologicsapp.ui.VistaAdministrador.AnimalesAdmViewModel
import com.dev.zoologicsapp.ui.adapters.AnimalAdapter

class FragmentListarAnimales : Fragment() {

    private var _binding: FragmentListarAnimalesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnimalesAdmViewModel
    private lateinit var animalAdapter: AnimalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AnimalesAdmViewModel::class.java]
        _binding = FragmentListarAnimalesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        animalAdapter = AnimalAdapter()
        binding.recyclerViewAnimales.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAnimales.adapter = animalAdapter

        viewModel.animales.observe(viewLifecycleOwner) { animales ->
            animalAdapter.setAnimales(animales)
            animales?.let {
                animalAdapter.setAnimales(it)
            }
        }

        viewModel.fetchAnimales()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}