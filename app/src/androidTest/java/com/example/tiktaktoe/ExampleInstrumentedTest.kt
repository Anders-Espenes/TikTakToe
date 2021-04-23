package com.example.tiktaktoe

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val TAG = "TEST"
    var gameState: Game? = null
    private val firstPlayer: String = "Ola Nordman"
    private val secondPlayer: String = "Sven Svenske"
    private val startingGameState: GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))

    @Test
    fun createGameTest() {
        Log.i(TAG, "CreateGameTest")
        var gotRequest = false
        GameService.createGame(firstPlayer, startingGameState) { state: Game?, err: Int? ->
            if(err != null) {
                Log.e(TAG, "Error code: $err")
            }
            checkGameNotNull(state)
            assertEquals(firstPlayer, state?.players?.get(0))
            assertEquals(startingGameState, state?.state)

            gameState = state
            gotRequest = true
        }
        sleep(1000) // Wait 2 sec for the response
        assert(gotRequest)  // Check if tests in the request response where run
    }

    @Test
    fun joinGameTest() {
        var gotRequest = false

        GameService.joinGame(secondPlayer, gameState!!.gameId) { state: Game?, err: Int? ->
            if(err != null) {
                throw Exception("Error code: $err")
            }
            checkGameNotNull(state)
            assertNotNull(firstPlayer, state?.players?.get(0))
            assertEquals(gameState?.gameId, state?.gameId ) // Check if joined game id is correct
            gotRequest = true
        }

        sleep(2000)
        assert(gotRequest)  // Check if tests in the request response where run
    }

    // Check if object have values
    private fun checkGameNotNull(state: Game?) {
        assertNotNull(state)
        assertNotNull(state?.gameId)
        assertNotNull(state?.state)
    }

}