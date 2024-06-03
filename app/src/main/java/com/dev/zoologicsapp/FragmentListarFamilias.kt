package com.dev.zoologicsapp

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarFamiliasBinding
import com.dev.zoologicsapp.ui.adapters.FamiliaAdapter
import com.dev.zoologicsapp.ui.familias.Familia
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

        familiaAdapter = FamiliaAdapter { familia ->
            showEditFamiliaDialog(requireContext(), familia) { newFamiliaName ->
                viewModel.updateFamilia(familia, newFamiliaName)
            }
        }
        binding.recyclerViewFamilias.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFamilias.adapter = familiaAdapter

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

    private fun showEditFamiliaDialog(context: android.content.Context, familia: Familia, onSave: (String) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_familia, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextModificarFamilia)

        editText.setText(familia.familia)

        val positiveColor = Color.parseColor("#2e89f3")
        val negativeColor = Color.parseColor("#AAAAAA")

        val positiveText = "Guardar" // Texto del botón positivo
        val negativeText = "Cancelar" // Texto del botón positivo

        val spannableString = SpannableString(positiveText)
        spannableString.setSpan(ForegroundColorSpan(positiveColor), 0, positiveText.length, 0)

        val negativeSpannable = SpannableString(negativeText)
        negativeSpannable.setSpan(ForegroundColorSpan(negativeColor), 0, negativeText.length, 0)

        AlertDialog.Builder(context)
            .setTitle("Modificar Familia")
            .setView(dialogView)
            .setPositiveButton(spannableString) { _, _ ->
                val newFamiliaName = editText.text.toString().trim()
                if (newFamiliaName.isNotEmpty()) {
                    onSave(newFamiliaName)
                }
            }
            .setNegativeButton(negativeSpannable, null)
            .create()
            .show()

    }

}