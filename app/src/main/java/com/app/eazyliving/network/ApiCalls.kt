package com.app.eazyliving.network

import android.util.Log
import com.app.eazyliving.model.DeviceList
import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.reflect.full.memberProperties

class ApiCalls(private val apiService: ApiService) {
    /*
        If response is successful (response.isSuccessful), return the User object extracted from the response body.
        If response is unsuccessful, show some fancy error message.
        If an exception is thrown, show some other fancy error message.

        You can call the login function and handle the response there without having to deal with the Retrofit stuff.
     */

    /*
        Example usage:
          val loginCredentials = LoginCredentials("email", "password")
            val user = apiCalls.login(loginCredentials)
            if (user != null) {
                // Handle successful login
            } else {
                // Handle unsuccessful login
            }
     */

    suspend fun login(loginCredentials: LoginCredentials): ResponseBody? {
        return try {
            val response = Retrofit.apiService.login(loginCredentials)
            println("response: $response")

            if (response.isSuccessful) {
                response.body()  // returns status 200 or okay if successful
            } else {
                println("Error response: ${response.errorBody()?.string()}")
                // Handle unsuccessful login by displaying an error message to the user.
                null
            }
        } catch (e: Exception) {
            // Handle errors by displaying an error message.
            null
        }
    }

    suspend fun logout() {
        try {
            val response = apiService.logout()
            if (response.isSuccessful) {
            // Send the user to the login screen.
            } else {
            // Handle unsuccessful request by displaying an error message to the user.
            }
        } catch (e: Exception) {
        // Handle errors by displaying an error message.
        }
    }


    suspend fun getSensors(): List<SensorData>? {
        val response = Retrofit.apiService.getSensors()
        Log.d("Sensors", response.toString())
        return try {

            if (response.isSuccessful) {
                val devicesResponse = response.body() // This should be of type Devices
                Log.d("test response", devicesResponse.toString())
                devicesResponse?.devices?.let { device ->
                    val sensorsList = mutableListOf<SensorData>()
                    Devices::class.memberProperties.forEach { property ->
                        val sensorName = property.name
                        val value = property.get(device)
                        when (value) {
                            is Boolean -> sensorsList.add(SensorData(sensorName, value))
                            is Int -> sensorsList.add(SensorData(sensorName, value > 0)) // Convert Int to Boolean; assume nonzero means "true"
                        }
                    }
                    return sensorsList // Return the constructed list of SensorData
                }
                null // Return null if devices is null
            } else {
                throw Exception("Failed to fetch sensors: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("SensorsError", "Error fetching sensors", e)
            null // Return null on exception
        }
    }

//    suspend fun updateSensors(): Boolean  {
//
//        return try {
//            val requestBody = mapOf("sensorName" to sensorName, "newState" to newState)
//            val response = apiService.updateSensors( )
//            response.isSuccessful
//        } catch (e: Exception) {
//            false
//        }
//    }
}