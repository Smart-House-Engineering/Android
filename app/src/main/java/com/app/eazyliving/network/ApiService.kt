package com.app.eazyliving.network

import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.LoginCredentials
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/*
    Interface that defines the API endpoints using Retrofit.

    ALL THESE ARE NOT YET FULLY FUNCTIONAL
*/

interface ApiService {
    @GET("/api/modes/defaultMode")
    suspend fun getSensors():Response<Devices> // <- returns list of Devices

    @POST("/auth/login")
    suspend fun login(@Body loginCredentials: LoginCredentials): Response<ResponseBody> // <- requires login credentials to be passed in the request body and returns a User object

    @POST("/auth/logout")
    suspend fun logout(): Response<Unit> // <- Unit is the return type of a void function

    @PUT("/api/homeUser/defaultMode")
    suspend fun updateSensors(@Body requestBody: Map<String, Any>): Response<Boolean>// <- TODO: fix this thing
}