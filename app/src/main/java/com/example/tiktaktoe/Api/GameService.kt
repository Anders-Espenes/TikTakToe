package com.example.tiktaktoe.Api

import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tiktaktoe.Api.data.Game
import com.example.tiktaktoe.Api.data.GameState
import com.example.tiktaktoe.App
import com.example.tiktaktoe.R
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject


typealias GameServiceCallback = (state: Game?, errorCode: Int?) -> Unit

object GameService {

    private val TAG: String = "GameService"

    // App context, easier to change context later if need be
    private val context = App.context

    // requestQue
    private val requestQue: RequestQueue = Volley.newRequestQueue(context)

    // Construct URL
    private enum class APIEndpoints(val url: String) {
        // Ex: https://generic-game-service.herokuapp.com/Game
        CREATE_GAME(
            "%1s%2s%3s".format(
                context.getString(R.string.protocol),
                context.getString(R.string.domain),
                context.getString(R.string.base_path)
            )
        ),

        // https://generic-game-service.herokuapp.com/Game/<gameId>/join
        JOIN_GAME(
            "%1s%2s%3s%4s".format(
                context.getString(R.string.protocol),
                context.getString(R.string.domain),
                context.getString(R.string.base_path),
                context.getString(R.string.join_game_path)
            )
        )
    }

    // POST request to create a game on the API
    fun createGame(playerId: String, state: GameState, callBack: GameServiceCallback) {

        // Get url to API
        val url = APIEndpoints.CREATE_GAME.url

        // Add player name and gamestate to request
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.putOpt("state", JSONArray(state))

        // Request Post, Url, Data, Headers
        val request = object : JsonObjectRequest(
            Method.POST, url, requestData,
            {
                // Sucess game created
                val game = Gson().fromJson(it.toString(), Game::class.java)
                Log.d(TAG, game.toString())
                callBack(game, null)
            }, {
                // Error creating new game
                callBack(null, it.networkResponse.statusCode)
            }) {
            // Add headers for the request
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }

    fun joinGame(playerId: String, gameId: String, callback: GameServiceCallback) {
        // Add gameId to URl
        val url = APIEndpoints.JOIN_GAME.url.format(gameId)
        Log.d(TAG, "URL: $url")

        // Add playername and gameId to request
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("gameId", gameId)

        val request = object : JsonObjectRequest(Method.POST, url, requestData,
            {
                // Success game joined
                val game = Gson().fromJson(it.toString(), Game::class.java)
                callback(game, null)
            }, {
                // Error joining a game
                callback(null, it.networkResponse.statusCode)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }

}

