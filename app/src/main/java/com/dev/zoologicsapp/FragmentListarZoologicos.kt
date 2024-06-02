package com.dev.zoologicsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarZoologicosBinding
import com.dev.zoologicsapp.ui.VistaAdministrador.ZoologicosAdmViewModel
import com.dev.zoologicsapp.ui.adapters.ZoologicoAdapter

class FragmentListarZoologicos : Fragment() {

    private var _binding: FragmentListarZoologicosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ZoologicosAdmViewModel
    private lateinit var zoologicoAdapter: ZoologicoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ZoologicosAdmViewModel::class.java]
        _binding = FragmentListarZoologicosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        zoologicoAdapter = ZoologicoAdapter()
        binding.recyclerViewZoologicos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewZoologicos.adapter = zoologicoAdapter

        viewModel.zoologicos.observe(viewLifecycleOwner) { zoologicos ->
            zoologicoAdapter.setZoologicos(zoologicos)
            zoologicos?.let {
                zoologicoAdapter.setZoologicos(it)
            }
        }

        viewModel.fetchZoologicos()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}