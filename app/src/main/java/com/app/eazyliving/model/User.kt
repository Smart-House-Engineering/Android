package com.app.eazyliving.model

data class User(
    val email: String,
    val password: String,
    val role: String,
    val homeId: String
)
