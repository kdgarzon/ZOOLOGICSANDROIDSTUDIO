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
import com.dev.zoologicsapp.databinding.FragmentAnimalesAdmBinding

class AnimalesAdm : Fragment() {

    private var _binding: FragmentAnimalesAdmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AnimalesAdmViewModel
    private lateinit var adapterItemsEspecies: ArrayAdapter<String>
    private lateinit var adapterItemsSexo: ArrayAdapter<String>
    private lateinit var adapterItemsContinente: ArrayAdapter<String>
    private lateinit var adapterItemsPais: ArrayAdapter<String>
    private lateinit var adapterItemsZoo: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[AnimalesAdmViewModel::class.java]

        _binding = FragmentAnimalesAdmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapterItemsEspecies = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        adapterItemsSexo = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        adapterItemsContinente = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        adapterItemsPais = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        adapterItemsZoo = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())

        binding.autoCompleteListEspecies.setAdapter(adapterItemsEspecies)
        binding.autoCompleteListSexo.setAdapter(adapterItemsSexo)
        binding.autoCompleteListContinentes.setAdapter(adapterItemsContinente)
        binding.autoCompleteListPais.setAdapter(adapterItemsPais)
        binding.autoCompleteListZoo.setAdapter(adapterItemsZoo)

        viewModel.especies.observe(viewLifecycleOwner) { especies ->
            adapterItemsEspecies.clear()
            adapterItemsEspecies.addAll(especies)
            adapterItemsEspecies.notifyDataSetChanged()
        }

        viewModel.sexo.observe(viewLifecycleOwner) { sexo ->
            adapterItemsSexo.clear()
            adapterItemsSexo.addAll(sexo)
            adapterItemsSexo.notifyDataSetChanged()
        }

        viewModel.continentes.observe(viewLifecycleOwner) { continentes ->
            adapterItemsContinente.clear()
            adapterItemsContinente.addAll(continentes)
            adapterItemsContinente.notifyDataSetChanged()
        }

        viewModel.paises.observe(viewLifecycleOwner) { paises ->
            adapterItemsPais.clear()
            adapterItemsPais.addAll(paises)
            adapterItemsPais.notifyDataSetChanged()
        }

        viewModel.zoologicos.observe(viewLifecycleOwner) { zoologicos ->
            adapterItemsZoo.clear()
            adapterItemsZoo.addAll(zoologicos)
            adapterItemsZoo.notifyDataSetChanged()
        }

        binding.autoCompleteListEspecies.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedEspecie.value = parent.getItemAtPosition(position).toString()
                val message = "Especie seleccionada: ${viewModel.selectedEspecie.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.autoCompleteListSexo.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedSex.value = parent.getItemAtPosition(position).toString()
                val message = "Sexo seleccionado: ${viewModel.selectedSex.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.autoCompleteListContinentes.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedContinente.value = parent.getItemAtPosition(position).toString()
                val message = "Continente seleccionado: ${viewModel.selectedContinente.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.autoCompleteListPais.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedPais.value = parent.getItemAtPosition(position).toString()
                val message = "Pais seleccionado: ${viewModel.selectedPais.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.autoCompleteListZoo.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedZoo.value = parent.getItemAtPosition(position).toString()
                val message = "Zoológico seleccionado: ${viewModel.selectedZoo.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.btnCrear.setOnClickListener {
            val nomCreado = binding.editTextNomAnimal.text.toString().trim()
            val anioCreado = binding.editTextAnio.text.toString().trim().toIntOrNull()

            if (anioCreado == null || nomCreado.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.crearAnimal(anioCreado, nomCreado)
            }
        }

        viewModel.animalCreationStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                Toast.makeText(
                    requireContext(),
                    "Animal creado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment_content_panel_administrador, AnimalesAdm())
                    commit()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "No se pudo crear el animal",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.fetchEspeciesFromFirestore()
        viewModel.fetchSexoFromFirestore()
        viewModel.fetchContinentesFromFirestore()
        viewModel.fetchPaisesFromFirestore()
        viewModel.fetchZoologicosFromFirestore()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}