package com.example.tiktaktoe.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tiktaktoe.R
import com.example.tiktaktoe.databinding.MenuFragmentBinding
import com.example.tiktaktoe.dialogs.CreateGameDialog
import com.example.tiktaktoe.dialogs.JoinGameDialog
import kotlinx.android.synthetic.main.menu_fragment.view.*

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

        view.createGame.setOnClickListener {
            createGame()
        }

        view.joinGame.setOnClickListener {
            joinGame()
        }



        return view
    }

    // Start creating a game
    private fun createGame() {
        val dlg = CreateGameDialog()
        dlg.show(parentFragmentManager, "CreateGameDialogManager")
        findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
    }

    // Start joining a game
    private fun joinGame() {
        val dlg = JoinGameDialog()
        dlg.show(parentFragmentManager, "JoinGameDialogManager")
        findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
    }

}