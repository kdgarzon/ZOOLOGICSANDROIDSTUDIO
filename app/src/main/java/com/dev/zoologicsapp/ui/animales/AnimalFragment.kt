package com.dev.zoologicsapp.ui.animales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentAnimalBinding

class AnimalFragment : Fragment() {

    private var _binding: FragmentAnimalBinding? = null

    private val binding get() = _binding!!


    //private val viewModel: FamiliaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val animalViewModel =
            ViewModelProvider(this)[AnimalViewModel::class.java]

        _binding = FragmentAnimalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAnimal
        animalViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}