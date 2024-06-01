package com.dev.zoologicsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zoologicsapp.databinding.FragmentListarUsuariosBinding
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

        usuarioAdapter = UsuarioAdapter()
        binding.recyclerViewUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsuarios.adapter = usuarioAdapter

        viewModel.usuarios.observe(viewLifecycleOwner, Observer { usuarios ->
            usuarioAdapter.setUsuarios(usuarios)
            usuarios?.let {
                usuarioAdapter.setUsuarios(it)
            }
        })

        viewModel.fetchUsuarios()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}