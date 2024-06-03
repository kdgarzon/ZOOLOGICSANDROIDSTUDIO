package com.dev.zoologicsapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarZoologicosBinding
import com.dev.zoologicsapp.ui.VistaAdministrador.Animal
import com.dev.zoologicsapp.ui.VistaAdministrador.Zoologico
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

        zoologicoAdapter = ZoologicoAdapter{zoologico ->
            showEditZoologicoDialog(requireContext(), zoologico) { updatedZoologico ->
                viewModel.updatedZoologico(updatedZoologico)
            }

        }
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

    private fun showEditZoologicoDialog(context: Context, zoologico: Zoologico, onSave: (Zoologico) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_zoologico, null)

        val editTextModNombreZoo = dialogView.findViewById<EditText>(R.id.editTextModificarNombreZoo)
        val autoCompleteListModificarPaises = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarPaises)
        val autoCompleteListModCiudades = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarCiudad)
        val editTextModificarTamaño = dialogView.findViewById<EditText>(R.id.editTextModificarTam)
        val editTextModificarPresupuesto = dialogView.findViewById<EditText>(R.id.editTextModificarPresupuesto)

        editTextModNombreZoo.setText(zoologico.nombrezoo)
        editTextModificarTamaño.setText(zoologico.tamañometroscuadrados.toString())
        editTextModificarPresupuesto.setText(zoologico.presupuesto.toString())

        // Observa los LiveData del ViewModel para llenar las listas desplegables
        viewModel.paises.observe(viewLifecycleOwner) { paises ->
            autoCompleteListModificarPaises.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, paises))
            autoCompleteListModificarPaises.setText(zoologico.pais, false)
        }

        viewModel.ciudades.observe(viewLifecycleOwner) { ciudades ->
            autoCompleteListModCiudades.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, ciudades))
            autoCompleteListModCiudades.setText(zoologico.ciudad, false)
        }

        AlertDialog.Builder(context)
            .setTitle("Modificar Zoológico")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val newNombre = editTextModNombreZoo.text.toString().trim()
                val newTamaño = editTextModificarTamaño.text.toString().trim().toIntOrNull()
                val newPresupuesto = editTextModificarPresupuesto.text.toString().trim().toIntOrNull()
                val newPais = autoCompleteListModificarPaises.text.toString().trim()
                val newCiudad = autoCompleteListModCiudades.text.toString().trim()

                if (newNombre.isNotEmpty() && newTamaño != null && newPresupuesto != null &&
                    newPais.isNotEmpty() && newCiudad.isNotEmpty()) {
                    val updatedZoologico = zoologico.copy(
                        nombrezoo = newNombre,
                        pais = newPais,
                        ciudad = newCiudad,
                        tamañometroscuadrados = newTamaño,
                        presupuesto = newPresupuesto
                    )
                    onSave(updatedZoologico)
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

}