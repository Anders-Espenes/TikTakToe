package com.example.tiktaktoe.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import com.example.tiktaktoe.GameManager
import com.example.tiktaktoe.R
import com.example.tiktaktoe.databinding.GameFragmentBinding
import com.example.tiktaktoe.dialogs.GameOverDialog


class GameFragment : Fragment(), View.OnClickListener {

    private val TAG = "GameFragment"

    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!

    private var gameState: Game? = null

    private var playerValue: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View {
        _binding = GameFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        // Set onClickListener for the buttons
        binding.apply {
            btn00.setOnClickListener(this@GameFragment)
            btn01.setOnClickListener(this@GameFragment)
            btn02.setOnClickListener(this@GameFragment)
            btn10.setOnClickListener(this@GameFragment)
            btn11.setOnClickListener(this@GameFragment)
            btn12.setOnClickListener(this@GameFragment)
            btn20.setOnClickListener(this@GameFragment)
            btn21.setOnClickListener(this@GameFragment)
            btn22.setOnClickListener(this@GameFragment)

        }

        GameManager.gameState.observe(viewLifecycleOwner, { game ->
            updateUi(game)
            gameState = game
            Log.d(TAG, "Updated Game: $game")
        })

        // Declare a winner
        GameManager.winner.observe(viewLifecycleOwner, { winner ->
            GameManager.stopPolling()   // Stop polling updates
            GameOverDialog(winner).show(parentFragmentManager, "GameOverDialogManager")
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        })
        return view
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                binding.btn00.id -> gameState?.state?.get(0)?.set(0, playerValue)
                binding.btn01.id -> gameState?.state?.get(0)?.set(1, playerValue)
                binding.btn02.id -> gameState?.state?.get(0)?.set(2, playerValue)
                binding.btn10.id -> gameState?.state?.get(1)?.set(0, playerValue)
                binding.btn11.id -> gameState?.state?.get(1)?.set(1, playerValue)
                binding.btn12.id -> gameState?.state?.get(1)?.set(2, playerValue)
                binding.btn20.id -> gameState?.state?.get(2)?.set(0, playerValue)
                binding.btn21.id -> gameState?.state?.get(2)?.set(1, playerValue)
                binding.btn22.id -> gameState?.state?.get(2)?.set(2, playerValue)
            }
        }

        gameState?.let { GameManager.updateGame(it) }
    }

    private fun updateUi(game: Game) {
        updateBoard(game.state)
        if (game.players.size == 2) {    // Set player names
            binding.player1.text = game.players[0]
            binding.player2.text = game.players[1]
            // Set if client is player 1 or 2
            playerValue = if (GameManager.client == game.players[0]) 1 else 2
        }
    }

    // Update board values displayed
    private fun updateBoard(board: GameState) {
        board.forEachIndexed { i, list ->
            list.forEachIndexed { j, value ->
                when ("$i$j") {
                    "00" -> binding.btn00.text = checkValue(value)
                    "01" -> binding.btn01.text = checkValue(value)
                    "02" -> binding.btn02.text = checkValue(value)
                    "10" -> binding.btn10.text = checkValue(value)
                    "11" -> binding.btn11.text = checkValue(value)
                    "12" -> binding.btn12.text = checkValue(value)
                    "20" -> binding.btn20.text = checkValue(value)
                    "21" -> binding.btn21.text = checkValue(value)
                    "22" -> binding.btn22.text = checkValue(value)
                }
            }
        }
    }

    private fun checkValue(value: Int): String {
        return when (value) {
            0 -> ""
            1 -> "X"
            2 -> "O"
            else -> "Error"
        }
    }
}
