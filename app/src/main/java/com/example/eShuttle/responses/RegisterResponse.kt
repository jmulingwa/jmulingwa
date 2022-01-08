package com.example.eShuttle.responses

data class RegisterResponse(
    val dateJoined: String,
    val email: String,
    val firstName: String,
    val login: String,
    val phone: String,
    val status: String

)