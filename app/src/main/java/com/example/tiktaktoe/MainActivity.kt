package com.example.tiktaktoe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import com.example.tiktaktoe.databinding.ActivityMainBinding
import com.example.tiktaktoe.dialogs.GameDialogListener


class MainActivity : AppCompatActivity() , GameDialogListener {

    private val TAG: String = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navHostFragment.findNavController()


        test()
    }

    override fun onDialogCreateGame(player: String) {
        GameManager.createGame(player)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        GameManager.joinGame(player, gameId)
    }

    fun test() {

        var gameState: Game? = null
        val firstPlayer: String = "Ola Nordman"
        val secondPlayer: String = "Sven Svenske"
        val startingState: GameState = mutableListOf(mutableListOf(0, 0, 0), mutableListOf(0, 0, 0), mutableListOf(0, 0, 0))
        lateinit var gameId: String
        GameService.createGame(firstPlayer, startingState) { state: Game?, err: Int? ->
            if (err != null)
                Log.e(TAG, "Error Code: $err")
            else {
                Log.d(TAG, "Created Game")
                if (state != null) {
                    gameId = state.gameId
                    gameState = state
                    GameService.joinGame(secondPlayer, gameId) { game1: Game?, err1: Int? ->
                        if(err1 != null)
                            Log.e(TAG, "Error Code: $err1")
                        else {
                            Log.d(TAG, "Joined Game")
                            if (game1 != null) {
                                gameState = state
                                gameState?.state = mutableListOf(mutableListOf(0, 2, 0), mutableListOf(0, 1, 0), mutableListOf(0, 0, 1))
                                Log.d(TAG, "Updated Game: ${gameState?.state.toString()}")
                                GameService.updateGame(gameState!!) { game2: Game?, err: Int? ->
                                    if(err != null)
                                        Log.e(TAG, "Error Code: $err")
                                    else {
                                        Log.d(TAG, "Return Update Game: ${game2?.state.toString()}")
                                        GameService.pollGame(gameState!!.gameId) {game3: Game?, err2: Int? ->
                                            if(err2 != null)
                                                Log.e(TAG, "Error Code: $err2")
                                            else {
                                                Log.d(TAG, "Polled Game: ${game3?.state.toString()}")
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}