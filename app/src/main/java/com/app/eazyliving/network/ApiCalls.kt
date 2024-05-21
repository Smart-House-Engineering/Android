package com.app.eazyliving.network

import android.util.Log
import com.app.eazyliving.model.DeviceList
import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.TimeoutException
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

suspend fun login(loginCredentials: LoginCredentials): String? {
    return try {
        val response = Retrofit.apiService.login(loginCredentials)
        println("responseLogin: $response")

        if (response.isSuccessful) {
            response.headers()["Set-Cookie"]
        } else {
            println("Error response: ${response.errorBody()?.string()}")
            null
        }
    } catch (e: Exception) {
        println("Exception: ${e.message}")
        null
    }
}


suspend fun getSensors(): List<SensorData>? {
    val maxRetries = 3
    var currentRetry = 0
    while (currentRetry < maxRetries) {
        try {
            // Set a timeout for the network request
            val response = withTimeout(5000) {
                Retrofit.apiService.getSensors()
            }
            Log.d("Sensors", "API response: $response")
            if (response.isSuccessful) {
                val devicesResponse = response.body()
                Log.d("test response", "Devices data: ${devicesResponse?.devices}")
                return devicesResponse?.devices?.let { device ->
                    val sensorsList = mutableListOf<SensorData>()
                    Devices::class.memberProperties.forEach { property ->
                        val sensorName = property.name
                        val value = property.get(device)
                        val switchStateInt = when (sensorName) {
                            "yellowLed", "window", "door", "gasSensor", "soilSensor", "steamSensor", "photocell" -> if (value is Int) value else null
                            else -> null
                        }
                        val switchStateBool = when (sensorName) {
                            "yellowLed", "window", "door" -> if (value is Int) value > 0 else (value as? Boolean)
                                ?: false

                            else -> value as? Boolean
                        }
                        sensorsList.add(
                            SensorData(
                                sensorName,
                                switchStateInt,
                                switchStateBool
                            )
                        )
                    }
                    sensorsList
                }
            } else {
                Log.e(
                    "SensorsError",
                    "Failed to fetch sensors: ${response.errorBody()?.string()}"
                )
            }
        } catch (e: TimeoutException) {
            Log.e("SensorsError", "Timeout while fetching sensors", e)
            currentRetry++
            delay(2000)
        } catch (e: Exception) {
            Log.e("SensorsError", "Exception while fetching sensors", e)
            break
        }
    }
    return null
}
}
