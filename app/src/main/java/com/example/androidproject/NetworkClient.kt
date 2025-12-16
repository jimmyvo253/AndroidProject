package com.example.androidproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://placeholder.com/") // must end with /
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: NetworkService = retrofit.create(NetworkService::class.java)
}
