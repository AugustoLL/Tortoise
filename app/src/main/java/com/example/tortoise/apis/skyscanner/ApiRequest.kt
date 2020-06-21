package com.example.tortoise.apis.skyscanner

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

object ApiRequest {

    private const val REQUEST_PROPERTY_HOST = "x-rapidapi-host"
    private const val REQUEST_PROPERTY_KEY = "x-rapidapi-key"
    private const val API_KEY = "664d7d8434mshd9ee201b1fa51c2p18cfd8jsn99804e637944"
    private const val API_HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com"
    private const val MY_URL = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/AR/EUR/en-US/MDZ-sky/anywhere/anytime?inboundpartialdate="
    private const val TAG = "API-RESULT"

    lateinit var skyScanner: SkyScannerAPI

    fun callSkyScannerAPI(){
        Log.i(TAG,"Entered callSkyScannerAPI()")
        Log.i(TAG,"Building Client")
        val client = OkHttpClient()

        Log.i(TAG,"Building Request")
        val request = Request.Builder()
            .url(MY_URL)
            .addHeader(REQUEST_PROPERTY_HOST, API_HOST)
            .addHeader(REQUEST_PROPERTY_KEY, API_KEY)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG,"ERROR, entered onFailure")
                Log.i(TAG,e.printStackTrace().toString())
            }
            override fun onResponse(call: Call, response: Response) {
                Log.i(TAG,"SUCCESS, entered onResponse")
                val gson = GsonBuilder().create()
                skyScanner = gson.fromJson(response.body?.string(), SkyScannerAPI::class.java)
            }
        })
    }
}