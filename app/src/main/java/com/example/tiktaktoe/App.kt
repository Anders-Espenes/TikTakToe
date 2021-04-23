package com.example.tiktaktoe

import android.app.Application

// Serves Application context around
class App: Application() {
    companion object{
        lateinit var context: App private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}