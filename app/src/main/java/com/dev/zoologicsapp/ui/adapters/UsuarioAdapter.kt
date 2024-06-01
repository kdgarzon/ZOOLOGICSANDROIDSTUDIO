package com.dev.zoologicsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.ui.VistaAdministrador.Usuario

class UsuarioAdapter : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {
    private var usuarios: List<Usuario> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_usuarios, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.bind(usuario)
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    fun setUsuarios(usuarios: List<Usuario>) {
        this.usuarios = usuarios
        notifyDataSetChanged()
    }

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreUsuario)
        private val apellidoTextView: TextView = itemView.findViewById(R.id.textViewApellidoUsuario)
        private val identificacionTextView: TextView = itemView.findViewById(R.id.textViewIdentUsuario)
        private val correoTextView: TextView = itemView.findViewById(R.id.textViewCorreoUsuario)
        private val userTextView: TextView = itemView.findViewById(R.id.textViewUsuarioUsuario)
        private val contraTextView: TextView = itemView.findViewById(R.id.textViewContraUsuario)
        private val rolTextView: TextView = itemView.findViewById(R.id.textViewRolUsuario)


        fun bind(usuario: Usuario) {

            nombreTextView.text = usuario.Nombre
            apellidoTextView.text = usuario.Apellido
            identificacionTextView.text = usuario.Identificacion
            correoTextView.text = usuario.Correo
            userTextView.text = usuario.Username
            contraTextView.text = usuario.Contraseña
            rolTextView.text = usuario.Rol
        }
    }
}