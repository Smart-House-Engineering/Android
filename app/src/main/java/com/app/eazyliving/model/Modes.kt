package com.app.eazyliving.model

data class SetModeRequest(
    val updatedModes: UpdatedModes
)

data class UpdatedModes(
    val emergency: Boolean
)

data class GetModeResponse(
    val message: String,
    val modes: Modes
)

data class Modes(
    val emergency: Boolean
)
