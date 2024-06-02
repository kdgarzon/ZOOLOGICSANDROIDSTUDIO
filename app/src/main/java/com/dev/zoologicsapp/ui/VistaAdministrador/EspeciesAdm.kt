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
import androidx.navigation.fragment.findNavController
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.databinding.FragmentEspeciesAdmBinding

class EspeciesAdm : Fragment() {

    private var _binding: FragmentEspeciesAdmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: EspeciesAdmViewModel
    private lateinit var adapterItemsFamilia: ArrayAdapter<String>
    private lateinit var adapterItemsEstado: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[EspeciesAdmViewModel::class.java]

        _binding = FragmentEspeciesAdmBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapterItemsFamilia = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        adapterItemsEstado = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())

        binding.autoCompleteListFamilia.setAdapter(adapterItemsFamilia)
        binding.autoCompleteListEstado.setAdapter(adapterItemsEstado)

        viewModel.familias.observe(viewLifecycleOwner) { familias ->
            adapterItemsFamilia.clear()
            adapterItemsFamilia.addAll(familias)
            adapterItemsFamilia.notifyDataSetChanged()
        }

        viewModel.estados.observe(viewLifecycleOwner) { estados ->
            adapterItemsEstado.clear()
            adapterItemsEstado.addAll(estados)
            adapterItemsEstado.notifyDataSetChanged()
        }

        binding.autoCompleteListFamilia.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedFamilia.value = parent.getItemAtPosition(position).toString()
                val message = "Familia seleccionada: ${viewModel.selectedFamilia.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.autoCompleteListEstado.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedEstado.value = parent.getItemAtPosition(position).toString()
                val message = "Estado seleccionado: ${viewModel.selectedEstado.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.btnCrear.setOnClickListener {
            val nomVulgarCreado = binding.editTextNomVulgar.text.toString().trim()
            val nomCientificoCreado = binding.editTextNomCientifico.text.toString().trim()

            if (nomVulgarCreado.isEmpty() || nomCientificoCreado.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.crearEspecie(nomVulgarCreado, nomCientificoCreado)
            }
        }

        binding.btnListarEspecies.setOnClickListener {
            findNavController().navigate(R.id.action_EspeciesAdm_to_FragmentListarEspecies)
        }

        viewModel.especieCreationStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                Toast.makeText(
                    requireContext(),
                    "Especie creada exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment_content_panel_administrador, EspeciesAdm())
                    commit()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "No se pudo crear la especie",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.fetchFamiliasFromFirestore()
        viewModel.fetchEstadosFromFirestore()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}