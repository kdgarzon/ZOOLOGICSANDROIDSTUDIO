package com.dev.zoologicsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarAnimalesBinding
import com.dev.zoologicsapp.databinding.FragmentListarEspeciesBinding
import com.dev.zoologicsapp.ui.VistaAdministrador.AnimalesAdmViewModel
import com.dev.zoologicsapp.ui.VistaAdministrador.EspeciesAdmViewModel
import com.dev.zoologicsapp.ui.adapters.AnimalAdapter
import com.dev.zoologicsapp.ui.adapters.EspecieAdapter

class FragmentListarEspecies : Fragment() {
    private var _binding: FragmentListarEspeciesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EspeciesAdmViewModel
    private lateinit var especieAdapter: EspecieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[EspeciesAdmViewModel::class.java]
        _binding = FragmentListarEspeciesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        especieAdapter = EspecieAdapter()
        binding.recyclerViewEspecies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEspecies.adapter = especieAdapter

        viewModel.especies.observe(viewLifecycleOwner, Observer { especies ->
            especieAdapter.setEspecies(especies)
            especies?.let {
                especieAdapter.setEspecies(it)
            }
        })

        viewModel.fetchEspecies()

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}