package com.example.androidproject

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class UserCredential(val email: String)

@Serializable
data class UserToken(val token: String)

