package com.dev.zoologicsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.ui.VistaAdministrador.Zoologico

class ZoologicoAdapter: RecyclerView.Adapter<ZoologicoAdapter.ZoologicoViewHolder>()  {
    private var zoologicos: List<Zoologico> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoologicoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_zoologicos, parent, false)
        return ZoologicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZoologicoViewHolder, position: Int) {
        val zoologico = zoologicos[position]
        holder.bind(zoologico)
    }

    override fun getItemCount(): Int {
        return zoologicos.size
    }

    fun setZoologicos(zoologicos: List<Zoologico>) {
        this.zoologicos = zoologicos
        notifyDataSetChanged()
    }

    class ZoologicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nombreZoologicoTextView: TextView = itemView.findViewById(R.id.textViewNombreZoologico)
        private val paisZoologicoTextView: TextView = itemView.findViewById(R.id.textViewPaisZoologico)
        private val ciudadZoologicoTextView: TextView = itemView.findViewById(R.id.textViewCiudadZoologico)
        private val tamañoZoologicoTextView: TextView = itemView.findViewById(R.id.textViewTamañoZoologico)
        private val presupuestoZoologicoTextView: TextView = itemView.findViewById(R.id.textViewPresupuestoZoologico)

        fun bind(zoologico: Zoologico) {

            nombreZoologicoTextView.text = zoologico.NombreZoo
            paisZoologicoTextView.text = zoologico.Pais
            ciudadZoologicoTextView.text = zoologico.Ciudad
            tamañoZoologicoTextView.text = zoologico.TamañoMetrosCuadrados.toString()
            presupuestoZoologicoTextView.text = zoologico.Presupuesto.toString()

        }
    }
}