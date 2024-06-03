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
import com.dev.zoologicsapp.databinding.FragmentListarUsuariosBinding
import com.dev.zoologicsapp.ui.VistaAdministrador.Usuario
import com.dev.zoologicsapp.ui.VistaAdministrador.UsuariosAdministradorViewModel
import com.dev.zoologicsapp.ui.adapters.UsuarioAdapter


class FragmentListarUsuarios : Fragment() {

    private var _binding: FragmentListarUsuariosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UsuariosAdministradorViewModel
    private lateinit var usuarioAdapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[UsuariosAdministradorViewModel::class.java]
        _binding = FragmentListarUsuariosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        usuarioAdapter = UsuarioAdapter{usuario ->
            showEditUsuarioDialog(requireContext(), usuario) { updatedUsuario ->
                viewModel.updateUsuario(updatedUsuario)
            }
        }
        binding.recyclerViewUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsuarios.adapter = usuarioAdapter

        viewModel.usuarios.observe(viewLifecycleOwner) { usuarios ->
            usuarioAdapter.setUsuarios(usuarios)
            usuarios?.let {
                usuarioAdapter.setUsuarios(it)
            }
        }

        viewModel.fetchUsuarios()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showEditUsuarioDialog(context: Context, usuario: Usuario, onSave: (Usuario) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_usuario, null)

        val editTextModIdent = dialogView.findViewById<EditText>(R.id.editTextModificarNumIdent)
        val editTextModNombre = dialogView.findViewById<EditText>(R.id.editTextModificarNombreUsuario)
        val editTextModApellido = dialogView.findViewById<EditText>(R.id.editTextModificarApellido)
        val editTextModUsuario = dialogView.findViewById<EditText>(R.id.editTextModificarUsername)
        val editTextModPassword = dialogView.findViewById<EditText>(R.id.editTextModificarContra)
        val editTextModCorreo = dialogView.findViewById<EditText>(R.id.editTextModificarEmail)
        val autoCompleteListModificarRol = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteListModificarRol)

        editTextModIdent.setText(usuario.identificacion)
        editTextModNombre.setText(usuario.nombre)
        editTextModApellido.setText(usuario.apellido)
        editTextModUsuario.setText(usuario.username)
        editTextModPassword.setText(usuario.contraseña)
        editTextModCorreo.setText(usuario.correo)

        // Observa los LiveData del ViewModel para llenar las listas desplegables
        viewModel.roles.observe(viewLifecycleOwner) { roles ->
            autoCompleteListModificarRol.setAdapter(ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, roles))
            autoCompleteListModificarRol.setText(usuario.rol, false)
        }


        AlertDialog.Builder(context)
            .setTitle("Modificar Usuario")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val newIdent = editTextModIdent.text.toString().trim()
                val newNombre = editTextModNombre.text.toString().trim()
                val newApellido = editTextModApellido.text.toString().trim()
                val newUsuario = editTextModUsuario.text.toString().trim()
                val newPassword = editTextModPassword.text.toString().trim()
                val newCorreo = editTextModCorreo.text.toString().trim()
                val newRol = autoCompleteListModificarRol.text.toString().trim()

                if (newNombre.isNotEmpty() && newIdent.isNotEmpty() &&
                    newApellido.isNotEmpty() && newUsuario.isNotEmpty() && newPassword.isNotEmpty() &&
                    newCorreo.isNotEmpty() && newRol.isNotEmpty()) {
                    val updatedUsuario = usuario.copy(
                        nombre = newNombre,
                        apellido = newApellido,
                        correo = newCorreo,
                        identificacion = newIdent,
                        username = newUsuario,
                        contraseña = newPassword,
                        rol = newRol
                    )
                    onSave(updatedUsuario)
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

}