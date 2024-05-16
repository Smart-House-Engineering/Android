package com.app.eazyliving.network

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import com.app.eazyliving.model.Devices
import com.app.eazyliving.model.DevicesResponse
import com.app.eazyliving.model.LoginCredentials
import com.app.eazyliving.model.SensorData

import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import retrofit2.Response
import okhttp3.Headers
import okhttp3.ResponseBody

/*
    login(LoginCredentials): test successful and unsuccessful login scenario.
    For the successful scenario, mock the ApiService to return a successful response and
    check if the method returns the expected result.

    For the unsuccessful scenario, mock the ApiService to return an error response and check if the method handles it correctly.

    logout(): Similar to login, test successful and unsuccessful scenarios.

    getSensors():
    test scenarios:
    method successfully retrieves the sensors,
    fails to retrieve the sensors,
    and when timeout occurs
 */

class ApiCallsTest {

    // Mock ApiService
    @Mock
    private lateinit var apiService: ApiService

    // Object to test
    private lateinit var apiCalls: ApiCalls

    // Init object and mocks
    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        apiCalls = ApiCalls(apiService)
    }

    // Check if login function works when server returns successful response
    @Test
    fun login_successful() {
        runBlocking {
            val loginCredentials = LoginCredentials("email", "password")
            val headers = Headers.headersOf("Set-Cookie", "cookie")
            val responseBody = ResponseBody.create(null, "")
            val response = Response.success(responseBody, headers)
            Mockito.`when`(apiService.login(loginCredentials)).thenReturn(response)

            val result = apiCalls.login(loginCredentials)

            assertEquals("cookie", result)
        }
    }

    // Check if login function handles error responses properly
    @Test
    fun login_unsuccessful() = runBlocking {
        val loginCredentials = LoginCredentials("email", "password")
        val responseBody = ResponseBody.create(null, "error")
        val response = Response.error<ResponseBody>(400, responseBody)
        Mockito.`when`(apiService.login(loginCredentials)).thenReturn(response)

        val result = apiCalls.login(loginCredentials)

        assertNull(result)
    }

    // Check if getSensors() works when the server returns successful response
    @Test
    fun getSensors_successful() = runBlocking {
        val devices = Devices(
            fan = true,
            yellowLed = 1,
            RFan = false,
            motion = true,
            buzzer = false,
            relay = true,
            door = 0,
            window = 1,
            gasSensor = 0,
            photocell = 1,
            soilSensor = 0,
            steamSensor = 1,
            whiteLed = true,
            button1 = false,
            button2 = true
        )
        val devicesResponse = DevicesResponse("Success", devices)
        val response = Response.success(devicesResponse)

        // Mocks apiService behavior
        Mockito.`when`(apiService.getSensors()).thenReturn(response)

        val result = apiCalls.getSensors()

        // Expected result
        val expected = listOf(
            SensorData("fan", true),
            SensorData("yellowLed", true),
            SensorData("RFan", false),
            SensorData("motion", true),
            SensorData("buzzer", false),
            SensorData("relay", true),
            SensorData("door", false),
            SensorData("window", true),
            SensorData("gasSensor", false),
            SensorData("photocell", true),
            SensorData("soilSensor", false),
            SensorData("steamSensor", true),
            SensorData("whiteLed", true),
            SensorData("button1", false),
            SensorData("button2", true)
        )

        // Assert expected result and actual result
        assertEquals(expected, result)
    }
}