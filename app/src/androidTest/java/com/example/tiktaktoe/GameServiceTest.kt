package com.example.tiktaktoe

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
var gameState: Game? = null // Passes gameState to next test, TODO: Find better solution

@RunWith(AndroidJUnit4::class)
class GameServiceTest {
    private val TAG = "GameServiceTest"
    private val firstPlayer: String = "Ola Nordman"
    private val secondPlayer: String = "Sven Svenske"
    private val startingGameState: GameState =
        listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))

    private var game1: Game =
        Game(mutableListOf(firstPlayer, secondPlayer), "3ndo7j", startingGameState) //


    @Test
    fun createGameTest() {
        Log.i(TAG, "CreateGameTest")
        var gotRequest = false
        GameService.createGame(firstPlayer, startingGameState) { state: Game?, err: Int? ->
            if (err != null) {
                Log.e(TAG, "Error code: $err")
            }
            checkGameNotNull(state)
            assertEquals(firstPlayer, state?.players?.get(0))
            assertEquals(startingGameState, state?.state)


            gameState = state   // update gamestate
            gotRequest = true

            Log.i(TAG, Gson().toJson(state))
        }
        sleep(1000) // Wait 1 sec for the response TODO: Find better solution
        assert(gotRequest)  // Check if tests in the request response where run
    }

    @Test
    fun joinGameTest() {
        var gotRequest = false

        GameService.joinGame(secondPlayer, gameState!!.gameId) { state: Game?, err: Int? ->
            if (err != null) {
                throw Exception("Error code: $err")
            }
            checkGameNotNull(state)
            assertEquals(firstPlayer, state?.players?.get(0))
            assertEquals(secondPlayer, state?.players?.get(1))
            assertEquals(gameState?.gameId, state?.gameId) // Check if joined game id is correct
            gotRequest = true
            gameState = state   // Update gamestate

            Log.i(TAG, Gson().toJson(state))
        }

        sleep(1000)
        assert(gotRequest)  // Check if tests in the request response where run
    }

    @Test
    fun updateGameTest() {
        var gotRequest = false

        game1.state = listOf(randomListOfNumbers(), randomListOfNumbers(), randomListOfNumbers())
        game1.let {
            GameService.updateGame(it) { state: Game?, err: Int? ->
                if (err != null) {
                    throw Exception("Error code: $err") //
                }
                checkGameNotNull(state)
                assertEquals(game1.gameId, state?.gameId)
                assertEquals(game1.players, state?.players) // Check if players are the same

                Log.i(TAG, "UPDATE: ${Gson().toJson(state)}")
                gotRequest = true
                gameState = state   // Update gamestate
            }
        }
        sleep(1000)
        assert(gotRequest)
    }

    @Test
    fun pollGameTest() {
        var gotRequest = false

        // Poll game1, check if
        GameService.pollGame(game1.gameId) { state: Game?, err: Int? ->
            if (err != null)
                throw Exception("Error code: $err")
            checkGameNotNull(state)
            assertEquals(game1.gameId, state?.gameId)
            assertEquals(game1.players, state?.players) // Check if players are the same
            assertNotEquals(    // Check if game state is different, true as long as state is not [[0,0,0],[0,0,0],[0,0,0]]
                game1.state,
                state?.state
            )
            gotRequest = true
        }
        sleep(1000)
        assert(gotRequest)
    }

    // Check if object have values
    private fun checkGameNotNull(state: Game?) {
        assertNotNull(state)
        assertNotNull(state?.gameId)
        assertNotNull(state?.state)
    }

    // Return list of three numbers between 0-2
    private fun randomListOfNumbers(): List<Int> {
        return (0..2).shuffled().take(4)
    }

}