package com.example.tiktaktoe

import android.widget.Toast
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState

object GameManager {
    var player:String? = null       // Client player name
    var state:GameState? = null     // Curent game state

    private val context = App.context

    // Create a game
    fun createGame(player:String){
        // Starting gamestate
        val startingGameState:GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))
        GameService.createGame(player, startingGameState) { game: Game?, err: Int? ->
            if(err != null) {   // Error code is returned
               errorHandler(err)
            } else {    // Success
               state = game
            }
        }
    }

    // Join a game
    fun joinGame(player:String, gameId:String){
        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if(err != null) {   // Error code is returned
                errorHandler(err)
            } else {    // Success

            }
        }
    }

    // Update a game
    fun updateGame(game: Game){
        GameService.updateGame(game) { game: Game?, err: Int? ->
            if(err != null) {   // Error code is returned
                errorHandler(err)
            } else {    // Success

            }
        }
    }

    // Poll a game
    fun pollGame(gameId:String){
        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if(err != null) {   // Error code is returned
                errorHandler(err)
            } else {    // Success

            }
        }
    }

    // Give messages dependent on error message received
    private fun errorHandler(err: Int){
        when (err){
            404 -> Toast.makeText(context, "Couldn't find game!", Toast.LENGTH_LONG).show()
            406, 511 -> Toast.makeText(context, "API key not accepted!", Toast.LENGTH_LONG).show()
            409 -> Toast.makeText(context, "Game is full!", Toast.LENGTH_LONG).show()
            500, 503 ->
                Toast.makeText(context, "Server error, try again later!", Toast.LENGTH_LONG).show()
            else ->
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()

        }
    }
}