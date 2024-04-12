package com.app.eazyliving.model

data class UpdateResponse(
    val message: String,
    val updatedHome: UpdatedHome
)

data class UpdatedHome(
    val devices: Devices,
    val _id: String,
    val homeId: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)
