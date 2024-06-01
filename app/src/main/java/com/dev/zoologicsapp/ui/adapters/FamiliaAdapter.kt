package com.dev.zoologicsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.ui.familias.Familia

//data class Familia(val nombre: String)
class FamiliaAdapter : RecyclerView.Adapter<FamiliaAdapter.FamiliaViewHolder>() {
    private var familias: List<Familia> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamiliaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FamiliaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamiliaViewHolder, position: Int) {
        val familia = familias[position]
        holder.bind(familia)
    }

    override fun getItemCount(): Int {
        return familias.size
    }

    fun setFamilias(familias: List<Familia>) {
        this.familias = familias
        notifyDataSetChanged()
    }

    class FamiliaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.textViewFamiliaNombre)

        fun bind(familia: Familia) {
            nombreTextView.text = familia.familia
        }
    }
}