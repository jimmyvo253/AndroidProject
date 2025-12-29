package com.example.androidproject.network

import com.example.androidproject.UserCredential
import com.example.androidproject.UserToken
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface NetworkService {

    @POST(".")
    suspend fun generateToken(
        @Url url: ("vhttps://egsbwqh7kildllpkijk6nt4soq0wlgpe.lambda-url.ap-southeast-1.on.aws/")
        @Body body: UserCredential
    ): UserToken
}
