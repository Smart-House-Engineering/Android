package com.app.eazyliving.model

import org.json.JSONArray

data class User(
    val email: String,
    val role: JSONArray?
)
