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

    }

    override fun onDialogCreateGame(player: String) {
        GameManager.createGame(player)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        GameManager.joinGame(player, gameId)
    }
}

