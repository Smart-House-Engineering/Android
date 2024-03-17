package com.app.eazyliving.network

import android.util.Log
import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData
import com.app.eazyliving.model.User
import okhttp3.ResponseBody

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

//    suspend fun login(loginCredentials: LoginCredentials): ResponseBody? {
//        return try {
//            val response = apiService.login(loginCredentials)
//            println("response: $response")
//
//            if (response.isSuccessful) {
//                response.body()  // returns status 200 or okay if successful
//            } else {
//                println("Error response: ${response.errorBody()?.string()}")
//                // Handle unsuccessful login by displaying an error message to the user.
//                null
//            }
//        } catch (e: Exception) {
//            // Handle errors by displaying an error message.
//            null
//        }
//    }

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
        return try {
            val response = apiService.getSensors()
            Log.d("Sensors", response.toString())
            if (response.isSuccessful) {

                val devices = response.body()
                devices?.let {
                    listOf(
                        SensorData("fan", it.fan),
                        SensorData("yellow LED", it.yellowLed> 0)
                    )
                    }
            } else {
                throw Exception("Failed to fetch sensors: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            // Handle errors by displaying an error message.
            null
        }
    }

    suspend fun updateSensors(sensorUpdate: SensorUpdate): Devices? {
        return try {
            val response = apiService.updateSensors(sensorUpdate)
            if (response.isSuccessful) {
                response.body() // Returns Devices object if successful.
            } else {
                // Handle unsuccessful request by displaying an error message to the user.
                null
            }
        } catch (e: Exception) {
            // Handle errors by displaying an error message.
            null
        }
    }
}