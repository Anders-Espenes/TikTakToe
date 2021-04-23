package com.example.tiktaktoe.Api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias GameState = List<List<Int>> // Holds the gameboard [[0,0,0],[0,0,0],[0,0,0]]

@Parcelize
data class Game(val players: MutableList<String>, val gameId: String, val state: List<List<Int>>) :
    Parcelable
