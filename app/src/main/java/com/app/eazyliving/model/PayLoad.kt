package com.app.eazyliving.model

data class UserPayload(val user: UserInfo)

data class UserInfo(
    val _id: String,
    val email: String,
    val role: String,
    val homeId: String,
    val __v: Int
)