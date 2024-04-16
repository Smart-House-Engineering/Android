package com.app.eazyliving.utility

import kotlinx.coroutines.delay
import retrofit2.Response

suspend fun <T> retryWithBackoff(
    maxRetries: Int = 3,
    startingDelay: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> Response<T>
): Response<T> {
    var currentDelay = startingDelay
    for (i in 0 until maxRetries) {
        val response = block()
        if (response.isSuccessful) {
            return response
        } else if (i == maxRetries - 1) {
            return response  // Return last attempt's response even if it's not successful
        } else {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong()
        }
    }
    throw IllegalStateException("This should never be reached")
}
