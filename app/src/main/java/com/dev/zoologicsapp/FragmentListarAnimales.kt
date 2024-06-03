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
import com.dev.zoologicsapp.databinding.FragmentListarAnimalesBinding
import com.dev.zoologicsapp.ui.VistaAdministrador.Animal
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

        animalAdapter = AnimalAdapter{animal ->
            showEditAnimalDialog(requireContext(), animal) { updatedAnimal ->
                viewModel.updateAnimal(updatedAnimal)
            }
        }
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

    private fun showEditAnimalDialog(context: Context, animal: Animal, onSave: (Animal) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_animal, null)

        val editTextModNombreAnimal = dialogView.findViewById<EditText>(R.id.editTextModificarNomAnimal)
        val editTextModAño = dialogView.findViewById<EditText>(R.id.editTextModificarAnio)
        val autoCompleteListModificarEspecies = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarEspecies)
        val autoCompleteListModificarSexo = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarSexo)
        val autoCompleteListModificarContinentes = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarContinentes)
        val autoCompleteListModificarPais = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarPais)
        val autoCompleteListModificarZoo = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarZoo)

        editTextModNombreAnimal.setText(animal.nombre_animal)
        editTextModAño.setText(animal.año.toString())

        // Observa los LiveData del ViewModel para llenar las listas desplegables
        viewModel.especies.observe(viewLifecycleOwner) { especies ->
            autoCompleteListModificarEspecies.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, especies))
            autoCompleteListModificarEspecies.setText(animal.especie, false)
        }

        viewModel.sexo.observe(viewLifecycleOwner) { sexos ->
            autoCompleteListModificarSexo.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, sexos))
            autoCompleteListModificarSexo.setText(animal.sexo, false)
        }

        viewModel.continentes.observe(viewLifecycleOwner) { continentes ->
            autoCompleteListModificarContinentes.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, continentes))
            autoCompleteListModificarContinentes.setText(animal.continente, false)
        }

        viewModel.paises.observe(viewLifecycleOwner) { paises ->
            autoCompleteListModificarPais.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, paises))
            autoCompleteListModificarPais.setText(animal.pais, false)
        }

        viewModel.zoologicos.observe(viewLifecycleOwner) { zoologicos ->
            autoCompleteListModificarZoo.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, zoologicos))
            autoCompleteListModificarZoo.setText(animal.zoo_Pertenece, false)
        }

        AlertDialog.Builder(context)
            .setTitle("Modificar Animal")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val newNombre = editTextModNombreAnimal.text.toString().trim()
                val newAño = editTextModAño.text.toString().trim().toIntOrNull()
                val newEspecie = autoCompleteListModificarEspecies.text.toString().trim()
                val newSexo = autoCompleteListModificarSexo.text.toString().trim()
                val newContinente = autoCompleteListModificarContinentes.text.toString().trim()
                val newPais = autoCompleteListModificarPais.text.toString().trim()
                val newZoo = autoCompleteListModificarZoo.text.toString().trim()

                if (newNombre.isNotEmpty() && newAño != null && newEspecie.isNotEmpty() &&
                    newSexo.isNotEmpty() && newContinente.isNotEmpty() && newPais.isNotEmpty() &&
                    newZoo.isNotEmpty()) {
                    val updatedAnimal = animal.copy(
                        nombre_animal = newNombre,
                        año = newAño,
                        especie = newEspecie,
                        sexo = newSexo,
                        continente = newContinente,
                        pais = newPais,
                        zoo_Pertenece = newZoo
                    )
                    onSave(updatedAnimal)
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

}