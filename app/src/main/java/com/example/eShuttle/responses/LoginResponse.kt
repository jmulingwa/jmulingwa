package com.example.eShuttle.responses

import com.example.eShuttle.models.User

data class LoginResponse(
    val id_token: String,
    val user : User?
)