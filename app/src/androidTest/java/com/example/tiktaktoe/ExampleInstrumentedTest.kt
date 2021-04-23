package com.example.tiktaktoe

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.google.gson.Gson
import junit.framework.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val TAG = "Test"
    var gameState: Game? = null
    val firstPlayer: String = "Ola Nordman"
    val secondPlayer: String = "Sven Svenske"
    val startingGameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))


    @Test
    fun createGameTest() {
        GameService.createGame(firstPlayer, startingGameState) {
                state: Game?, err:Int? ->
            gameState = state
            assertNotNull(state)
            assertNotNull(state?.gameId)
            assertEquals(firstPlayer, state?.players?.get(0))
            assertEquals("{\"nameValuePairs\":{\"player\":\"Ola Nordman\",\"state\": [[0,0,0],[0,0,0],[0,0,0]]}}", Gson().toJson(state))
        }
    }


}