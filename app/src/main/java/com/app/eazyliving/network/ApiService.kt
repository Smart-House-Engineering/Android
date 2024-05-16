package com.app.eazyliving.network

import com.app.eazyliving.model.DevicesResponse
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.UpdateResponse
import com.app.eazyliving.model.UserCredentials
import com.app.eazyliving.model.UserDetails
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/*
    Interface that defines the API endpoints using Retrofit.

    ALL THESE ARE NOT YET FULLY FUNCTIONAL
*/

interface ApiService {
    @GET("/api/modes/defaultMode")
    suspend fun getSensors():Response<DevicesResponse> // <- returns list of Devices

    @POST("/auth/login")
    suspend fun login(@Body loginCredentials: LoginCredentials): Response<ResponseBody> // <- requires login credentials to be passed in the request body and returns a User object

    @POST("/auth/logout")
    suspend fun logout(): Response<ResponseBody>
    @PUT("/api/homeUser/defaultMode")
    @JvmSuppressWildcards
    suspend fun updateSensors(@Body updatedDevices: Map<String, Map<String, Any>>): Response<UpdateResponse>// <- TODO: fix this thing

    @POST("/api/owner/addUser/")
    suspend fun addUsers(@Body userData: UserCredentials): Response<ResponseBody>

    @GET("/api/user/allMembers")
    suspend fun getUsers():Response<List<UserDetails>>
}



