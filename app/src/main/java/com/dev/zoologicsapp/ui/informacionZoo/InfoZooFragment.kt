package com.dev.zoologicsapp.ui.informacionZoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.zoologicsapp.databinding.FragmentInfozooBinding

class InfoZooFragment : Fragment() {

    private var _binding: FragmentInfozooBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoZooViewModel =
            ViewModelProvider(this)[InfoZooViewModel::class.java]

        _binding = FragmentInfozooBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textInfoZoo
        infoZooViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}