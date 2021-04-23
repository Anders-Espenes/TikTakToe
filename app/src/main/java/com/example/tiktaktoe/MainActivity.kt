package com.example.tiktaktoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktaktoe.Api.GameService
import com.example.tiktaktoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val TAG:String = "MainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GameService.sendRequest()
    }
}