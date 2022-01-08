package com.example.eShuttle.responses

import com.example.eShuttle.models.User

data class getUserResponse(
    val activated: Boolean,
    val authorities: List<String>,
    val createdBy: String,
    val createdDate: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val imageUrl: Any,
    val langKey: String,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lastName: Any,
    val login: String,
)