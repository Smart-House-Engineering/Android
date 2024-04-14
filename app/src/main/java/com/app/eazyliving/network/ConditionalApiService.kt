package com.app.eazyliving.network


import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object SessionManager {
    var isLoggedIn: Boolean = false
}

abstract class ConditionalApiService(private val realApiService: ApiService) : ApiService {

    override suspend fun logout(): Response<ResponseBody> {
        return if (SessionManager.isLoggedIn) {
            realApiService.logout().also {
                if (it.isSuccessful) {
                    SessionManager.isLoggedIn = false
                }
            }
        } else {

            Response.error(403, "Not logged in".toResponseBody())
        }
    }

    // Implement other methods similarly
}
