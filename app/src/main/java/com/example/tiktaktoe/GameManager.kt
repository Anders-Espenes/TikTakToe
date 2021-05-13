package com.example.tiktaktoe

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState

object GameManager {
    var client: String? = null       // Client player name
    var state: GameState? = null     // Curent game state

    // Contain game state, current players, gameId and board state
    private var _gameState = MutableLiveData<Game>()
    val gameState: LiveData<Game> get() = _gameState

    // Is assigned to the winner of the game
    private var _winner = MutableLiveData<String>()
    val winner: LiveData<String> get() = _winner


    private val context = App.context

    // Create a game
    fun createGame(player: String) {
        // Starting gamestate
        val startingGameState: GameState =
            mutableListOf(mutableListOf(0, 0, 0), mutableListOf(0, 0, 0), mutableListOf(0, 0, 0))
        GameService.createGame(player, startingGameState) { game: Game?, err: Int? ->
            if (err != null) {   // Error code is returned
                errorHandler(err)
            } else if (game != null) {    // Success
                client = player
                _gameState.value = game
            }
        }
    }

    // Join a game
    fun joinGame(player: String, gameId: String) {
        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {   // Error code is returned
                errorHandler(err)
            } else if (game != null) {    // Success
                _gameState.value = game
            }
        }
    }

    // Update a game
    fun updateGame(game: Game) {
        GameService.updateGame(game) { game: Game?, err: Int? ->
            if (err != null) {   // Error code is returned
                errorHandler(err)
            } else if (game != null) {    // Success
                _gameState.value = game
            }
        }
    }

    // Poll a game
    fun pollGame(gameId: String) {
        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if (err != null) {   // Error code is returned
                errorHandler(err)
            } else if (game != null) {    // Success
                _gameState.value = game
                when(checkWinner(game.state)) { // Check for winner
                    1 -> _winner.value = game.players[0] // Player 1
                    2 -> _winner.value = game.players[1] // Player 2
                    null -> _winner.value = "Tie"        // Tied game
                }
            }
        }
    }

    // Give messages dependent on error message received
    private fun errorHandler(err: Int) {
        when (err) {
            404 -> Toast.makeText(context, "Couldn't find game!", Toast.LENGTH_LONG).show()
            406, 511 -> Toast.makeText(context, "API key not accepted!", Toast.LENGTH_LONG).show()
            409 -> Toast.makeText(context, "Game is full!", Toast.LENGTH_LONG).show()
            500, 503 ->
                Toast.makeText(context, "Server error, try again later!", Toast.LENGTH_LONG).show()
            else ->
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()

        }
    }

    private fun checkWinner(gameState: GameState) : Int? {
        // True if three input values are equal and not 0
        fun check(first: Int, second: Int, third: Int) : Boolean {
            return (first != 0 && first == second && first == third)
        }
        // Horizontal check
        for (i in 0..2) {
            if(check(gameState[i][0], gameState[i][1], gameState[i][2]))
                return gameState[i][0]
        }
        // Vertical check
        for (i in 0..2) {
            if(check(gameState[0][i], gameState[1][i], gameState[2][i]))
                return gameState[0][i]
        }
        // Diagonal check
        if(check(gameState[0][0], gameState[1][1], gameState[2][2]))
            return gameState[0][0]
        if(check(gameState[0][2], gameState[1][1], gameState[2][0]))
            return gameState[0][2]

        // Tie check, returns 0 if any remains, returns null of board is filled
       return gameState.flatten().firstOrNull{it == 0}
    }


}