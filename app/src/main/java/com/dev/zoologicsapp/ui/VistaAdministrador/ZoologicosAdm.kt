package com.dev.zoologicsapp.ui.VistaAdministrador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.databinding.FragmentZoologicosAdmBinding

class ZoologicosAdm : Fragment() {

    private var _binding: FragmentZoologicosAdmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: ZoologicosAdmViewModel
    private lateinit var adapterItemsCiudades: ArrayAdapter<String>
    private lateinit var adapterItemsPaises: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[ZoologicosAdmViewModel::class.java]

        _binding = FragmentZoologicosAdmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapterItemsCiudades = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        adapterItemsPaises = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())

        binding.autoCompleteListCiudad.setAdapter(adapterItemsCiudades)
        binding.autoCompleteListPaises.setAdapter(adapterItemsPaises)

        viewModel.ciudades.observe(viewLifecycleOwner) { ciudades ->
            adapterItemsCiudades.clear()
            adapterItemsCiudades.addAll(ciudades)
            adapterItemsCiudades.notifyDataSetChanged()
        }

        viewModel.paises.observe(viewLifecycleOwner) { paises ->
            adapterItemsPaises.clear()
            adapterItemsPaises.addAll(paises)
            adapterItemsPaises.notifyDataSetChanged()
        }

        binding.autoCompleteListCiudad.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedCiudad.value = parent.getItemAtPosition(position).toString()
                val message = "Ciudad seleccionada: ${viewModel.selectedCiudad.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.autoCompleteListPaises.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedPais.value = parent.getItemAtPosition(position).toString()
                val message = "Pais seleccionado: ${viewModel.selectedPais.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.btnCrear.setOnClickListener {
            val nomCreado = binding.editTextNombreZoo.text.toString().trim()
            val tamCreado = binding.editTextTam.text.toString().trim().toIntOrNull()
            val presupuestoCreado = binding.editTextPresupuesto.text.toString().trim().toIntOrNull()

            if (tamCreado == null || nomCreado.isEmpty() || presupuestoCreado == null||
                viewModel.selectedCiudad.value.isNullOrEmpty() ||
                viewModel.selectedPais.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.crearZoo(tamCreado, nomCreado, presupuestoCreado)
            }
        }

        viewModel.zooCreationStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                Toast.makeText(
                    requireContext(),
                    "Zoológico creado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment_content_panel_administrador, ZoologicosAdm())
                    commit()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "No se pudo crear el zoológico",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.fetchCiudadesFromFirestore()
        viewModel.fetchPaisesFromFirestore()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}