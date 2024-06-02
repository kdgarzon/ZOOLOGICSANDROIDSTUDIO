package com.dev.zoologicsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarFamiliasBinding
import com.dev.zoologicsapp.ui.adapters.FamiliaAdapter
import com.dev.zoologicsapp.ui.familias.FamiliaViewModel

class FragmentListarFamilias : Fragment() {

    private var _binding: FragmentListarFamiliasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FamiliaViewModel
    private lateinit var familiaAdapter: FamiliaAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[FamiliaViewModel::class.java]
        _binding = FragmentListarFamiliasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        familiaAdapter = FamiliaAdapter()
        binding.recyclerViewFamilias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFamilias.adapter = familiaAdapter

        /*viewModel.familias.observe(viewLifecycleOwner) { familias ->
            familiaAdapter.setFamilias(familias)
        }*/

        viewModel.familias.observe(viewLifecycleOwner) { familias ->
            familias?.let {
                familiaAdapter.setFamilias(it)
            }
        }

        viewModel.fetchFamilias()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}