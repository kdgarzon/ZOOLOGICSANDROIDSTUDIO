package com.dev.zoologicsapp.ui.familias

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.databinding.FragmentFamiliaBinding

class FamiliaFragment : Fragment() {

    private var _binding: FragmentFamiliaBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: FamiliaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[FamiliaViewModel::class.java]

        _binding = FragmentFamiliaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnCrear.setOnClickListener {
            val familiaCreada = binding.editTextFamilia.text.toString().trim()

            if (familiaCreada.isEmpty() ) {
                Toast.makeText(requireContext(), "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.crearFamilia(familiaCreada)
            }
        }

        binding.btnListarFamilias.setOnClickListener{
            findNavController().navigate(R.id.action_familiaFragment_to_fragmentListarFamilias)
        }

        viewModel.familiaCreationStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                Toast.makeText(requireContext(), "Familia creada exitosamente", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment_content_panel_zoo, FamiliaFragment())
                    commit()
                }
            } else {
                Toast.makeText(requireContext(), "No se pudo crear la familia", Toast.LENGTH_SHORT).show()
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}