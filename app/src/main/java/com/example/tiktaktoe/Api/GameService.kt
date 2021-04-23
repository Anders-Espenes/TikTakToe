package com.example.tiktaktoe.Api

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tiktaktoe.App


object GameService {

    private val TAG: String = "GameService"

    // App context, easier to change context later if need be
    private val context = App.context

    // requestQue
    private val requestQue: RequestQueue = Volley.newRequestQueue(context)
    val url = "https://www.nrk.no/"

    fun sendRequest() {
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d(TAG, "Response is: ${response.substring(0, 500)}")
            },
            { Log.e(TAG, it.toString()) })

        requestQue.add(stringRequest)
    }


}

