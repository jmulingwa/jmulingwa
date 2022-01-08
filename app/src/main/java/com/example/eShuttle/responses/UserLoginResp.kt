package com.example.eShuttle.responses

import com.example.eShuttle.models.User

data class UserLoginResp(
    val id: Int,
    val login: Any,
    val user : User?
)