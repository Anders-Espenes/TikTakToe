package com.example.tiktaktoe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiktaktoe.databinding.MenuFragmentBinding

class MenuFragment : Fragment() {
    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View? {
        _binding = MenuFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root


        return view
    }
}