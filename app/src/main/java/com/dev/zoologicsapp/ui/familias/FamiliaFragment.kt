package com.dev.zoologicsapp.ui.familias

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentFamiliaBinding

class FamiliaFragment : Fragment() {

    private var _binding: FragmentFamiliaBinding? = null

    private val binding get() = _binding!!


    //private val viewModel: FamiliaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val familiaViewModel =
            ViewModelProvider(this)[FamiliaViewModel::class.java]

        _binding = FragmentFamiliaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFamilia
        familiaViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}