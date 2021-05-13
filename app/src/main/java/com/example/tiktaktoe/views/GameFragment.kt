package com.example.tiktaktoe.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import com.example.tiktaktoe.GameManager
import com.example.tiktaktoe.databinding.GameFragmentBinding


class GameFragment : Fragment(), View.OnClickListener {

    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!

    private var gameState: Game? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View? {
        _binding = GameFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root


        // Set onClickListener for the buttons
        binding.apply {
            btn11.setOnClickListener(this@GameFragment)
            btn12.setOnClickListener(this@GameFragment)
            btn13.setOnClickListener(this@GameFragment)
            btn21.setOnClickListener(this@GameFragment)
            btn22.setOnClickListener(this@GameFragment)
            btn23.setOnClickListener(this@GameFragment)
            btn31.setOnClickListener(this@GameFragment)
            btn32.setOnClickListener(this@GameFragment)
            btn33.setOnClickListener(this@GameFragment)

        }

        GameManager.gameState.observe(viewLifecycleOwner, { game ->
            updateUi(game)
            gameState = game

        })

        return view
    }

    override fun onClick(v: View?) {
        gameState?.let { GameManager.updateGame(it) }
    }

    private fun updateUi(game: Game) {
       updateBoard(game.state)
    }

    // Update board values displayed
    private fun updateBoard(board: GameState) {
        board.forEachIndexed { i, list ->
            list.forEachIndexed { j, value ->

                when ("$i$j") {
                    "00" -> binding.btn11.text = checkValue(value)
                    "01" -> binding.btn12.text = checkValue(value)
                    "02" -> binding.btn13.text = checkValue(value)
                    "10" -> binding.btn21.text = checkValue(value)
                    "11" -> binding.btn22.text = checkValue(value)
                    "12" -> binding.btn23.text = checkValue(value)
                    "20" -> binding.btn31.text = checkValue(value)
                    "21" -> binding.btn32.text = checkValue(value)
                    "22" -> binding.btn33.text = checkValue(value)
                }
            }
        }
    }

    private fun checkValue(value: Int): String{
        return when(value) {
            0 -> ""
            1 -> "X"
            2 -> "O"
            else -> ""
        }
    }
}
