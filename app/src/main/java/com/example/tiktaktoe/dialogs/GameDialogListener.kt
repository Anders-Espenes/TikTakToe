package com.example.tiktaktoe.dialogs

interface GameDialogListener {
    fun onDialogCreateGame(player: String)
    fun onDialogJoinGame(player: String, gameId: String)
}