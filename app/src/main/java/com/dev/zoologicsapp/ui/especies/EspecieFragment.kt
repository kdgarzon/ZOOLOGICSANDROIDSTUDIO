package com.dev.zoologicsapp.ui.especies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentEspecieBinding

class EspecieFragment : Fragment() {
    private var _binding: FragmentEspecieBinding? = null

    private val binding get() = _binding!!


    //private val viewModel: FamiliaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val especieViewModel =
            ViewModelProvider(this)[EspecieViewModel::class.java]

        _binding = FragmentEspecieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEspecie
        especieViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}