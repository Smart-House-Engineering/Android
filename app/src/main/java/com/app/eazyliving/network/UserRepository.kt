package com.app.eazyliving.network

import com.app.eazyliving.model.LoginCredentials

class UserRepository(private val apiService: ApiService) {
    suspend fun loginUser(credentials: LoginCredentials): String? {
        val response = apiService.login(credentials)
        if (response.isSuccessful) {
            // Extract JWT from cookie header or body depending on your API
            val jwtToken = response.headers()["Set-Cookie"] // Or any other way your JWT is delivered
            // Perform operations to extract and return the token
            return jwtToken
        }
        return null
    }
}