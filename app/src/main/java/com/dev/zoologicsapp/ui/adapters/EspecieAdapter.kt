package com.dev.zoologicsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.ui.VistaAdministrador.Especie

class EspecieAdapter: RecyclerView.Adapter<EspecieAdapter.EspecieViewHolder>() {
    private var especies: List<Especie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EspecieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_especies, parent, false)
        return EspecieViewHolder(view)
    }

    override fun onBindViewHolder(holder: EspecieViewHolder, position: Int) {
        val especie = especies[position]
        holder.bind(especie)
    }

    override fun getItemCount(): Int {
        return especies.size
    }

    fun setEspecies(especies: List<Especie>) {
        this.especies = especies
        notifyDataSetChanged()
    }

    class EspecieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreVulgarTextView: TextView = itemView.findViewById(R.id.textViewNombreVulgarEspecie)
        private val nombreCientificoTextView: TextView = itemView.findViewById(R.id.textViewNombreCientificoEspecie)
        private val familiaTextView: TextView = itemView.findViewById(R.id.textViewFamiliaEspecie)
        private val peligroTextView: TextView = itemView.findViewById(R.id.textViewPeligroEspecie)

        fun bind(especie: Especie) {

            nombreVulgarTextView.text = especie.Nombre_vulgar
            nombreCientificoTextView.text = especie.Nombre_Cientifico
            familiaTextView.text = especie.Familia
            peligroTextView.text = especie.Nivel_Peligro

        }
    }
}