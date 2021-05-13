package com.example.tiktaktoe.Api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias GameState = MutableList<MutableList<Int>> // Holds the gameboard [[0,0,0],[0,0,0],[0,0,0]]

@Parcelize
data class Game(val players: MutableList<String>, val gameId: String, var state: GameState) :
    Parcelable

