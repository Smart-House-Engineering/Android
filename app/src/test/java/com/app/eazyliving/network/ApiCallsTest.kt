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

    // Check that the returned list of SensorData matches the expected list
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

        Mockito.`when`(apiService.getSensors()).thenReturn(response)

        val result = apiCalls.getSensors()

        val expected = listOf(
            SensorData("fan", null, true),
            SensorData("yellowLed", 1, null),
            SensorData("RFan", null, false),
            SensorData("motion", null, true),
            SensorData("buzzer", null, false),
            SensorData("relay", null, true),
            SensorData("door", 0, null),
            SensorData("window", 1, null),
            SensorData("gasSensor", 0, null),
            SensorData("photocell", 1, null),
            SensorData("soilSensor", 0, null),
            SensorData("steamSensor", 1, null),
            SensorData("whiteLed", null, true),
            SensorData("button1", null, false),
            SensorData("button2", null, true)
        )

        assertEquals(expected, result)
    }

    // Check that the result is null when the server returns an error response
    @Test
    fun getSensors_unsuccessful() = runBlocking {
        val responseBody = ResponseBody.create(null, "error")
        val response = Response.error<DevicesResponse>(400, responseBody)

        Mockito.`when`(apiService.getSensors()).thenReturn(response)

        val result = apiCalls.getSensors()

        assertNull(result)
    }
}