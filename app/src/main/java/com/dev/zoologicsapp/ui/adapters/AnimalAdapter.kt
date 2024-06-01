package com.dev.zoologicsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.zoologicsapp.R
import com.dev.zoologicsapp.ui.VistaAdministrador.Animal

class AnimalAdapter: RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {
    private var animales: List<Animal> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_animales, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animales[position]
        holder.bind(animal)
    }

    override fun getItemCount(): Int {
        return animales.size
    }

    fun setAnimales(animales: List<Animal>) {
        this.animales = animales
        notifyDataSetChanged()
    }

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreAnimal)
        private val especieTextView: TextView = itemView.findViewById(R.id.textViewEspecieAnimal)
        private val sexoTextView: TextView = itemView.findViewById(R.id.textViewSexoAnimal)
        private val continenteTextView: TextView = itemView.findViewById(R.id.textViewContinenteAnimal)
        private val paisTextView: TextView = itemView.findViewById(R.id.textViewPaisAnimal)
        private val añoTextView: TextView = itemView.findViewById(R.id.textViewAñoAnimal)
        private val zooTextView: TextView = itemView.findViewById(R.id.textViewZooAnimal)

        fun bind(animal: Animal) {

            nombreTextView.text = animal.Nombre_animal
            especieTextView.text = animal.Especie
            sexoTextView.text = animal.Sexo
            continenteTextView.text = animal.Continente
            paisTextView.text = animal.Pais
            añoTextView.text = animal.Año.toString()
            zooTextView.text = animal.Zoo_Pertenece
        }
    }
}