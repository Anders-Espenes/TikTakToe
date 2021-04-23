package com.example.tiktaktoe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import com.example.tiktaktoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        test()
    }

    fun test() {

        var gameState: Game? = null
        val firstPlayer: String = "Ola Nordman"
        val secondPlayer: String = "Sven Svenske"
        val startingState: GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))
        lateinit var gameId: String
        GameService.createGame(firstPlayer, startingState) { state: Game?, err: Int? ->
            if (err != null)
                Log.e(TAG, "Error Code: $err")
            else {
                Log.d(TAG, "Created Game")
                if (state != null) {
                    gameId = state.gameId
                    gameState = state
                    GameService.joinGame(secondPlayer, gameId) { state: Game?, err: Int? ->
                        if(err != null)
                            Log.e(TAG, "Error Code: $err")
                        else {
                            Log.d(TAG, "Joined Game")
                        }
                    }
                }
            }
        }
    }
}