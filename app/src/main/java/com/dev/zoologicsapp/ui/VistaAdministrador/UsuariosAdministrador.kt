package com.dev.zoologicsapp.ui.VistaAdministrador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.databinding.FragmentUsuariosAdministradorBinding

class UsuariosAdministrador : Fragment() {

    private var _binding: FragmentUsuariosAdministradorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: UsuariosAdministradorViewModel
    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[UsuariosAdministradorViewModel::class.java]

        _binding = FragmentUsuariosAdministradorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapterItems = ArrayAdapter(requireContext(), R.layout.lista_roles, arrayListOf())
        binding.autoCompleteList.setAdapter(adapterItems)

        viewModel.roles.observe(viewLifecycleOwner, Observer { roles ->
            adapterItems.clear()
            adapterItems.addAll(roles)
        })

        binding.autoCompleteList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.selectedRole.value = parent.getItemAtPosition(position).toString()
                val message = "Item seleccionado: ${viewModel.selectedRole.value}"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        binding.btnCrear.setOnClickListener {
            val identCreada = binding.editTextNumIdent.text.toString().trim()
            val nomCreado = binding.editTextNombre.text.toString().trim()
            val apelCreado = binding.editTextApellido.text.toString().trim()
            val userCreado = binding.editTextUsername.text.toString().trim()
            val contraCreada = binding.editTextContra.text.toString().trim()
            val correoCreado = binding.editTextEmail.text.toString().trim()

            if (identCreada.isEmpty() || nomCreado.isEmpty() || apelCreado.isEmpty() || userCreado.isEmpty() || contraCreada.isEmpty() || correoCreado.isEmpty() || viewModel.selectedRole.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete los campos", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.crearUsuario(identCreada, nomCreado, apelCreado, userCreado, contraCreada, correoCreado)
            }
        }

        binding.btnListarUsuarios.setOnClickListener{
            findNavController().navigate(R.id.action_UsuariosAdministrador_to_FragmentListarUsuarios)
        }

        viewModel.userCreationStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                Toast.makeText(requireContext(), "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment_content_panel_administrador, UsuariosAdministrador())
                    commit()
                }
            } else {
                Toast.makeText(requireContext(), "No se pudo crear el usuario", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.fetchRolesFromFirestore()

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}