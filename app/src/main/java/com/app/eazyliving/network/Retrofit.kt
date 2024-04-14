package com.app.eazyliving.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.app.eazyliving.network.Cookies.AddCookiesInterceptor
import com.app.eazyliving.network.Cookies.ReceivedCookiesInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit

/*
    Retrofit instance to make API calls.
    The base URL is the endpoint of the backend.
    GsonConverterFactory is used to convert JSON to data models.
 */

/*
    Creates a singleton instance of Retrofit.
    Ensures that only one instance of Retrofit is created throughout the lifecycle.
    Should improve performance/efficiency.
    Uses 'lazy' thread-safety.
 */

object Retrofit {

    @SuppressLint("StaticFieldLeak")
    private lateinit var cookieInterceptor: ReceivedCookiesInterceptor
    @SuppressLint("StaticFieldLeak")
    private lateinit var addCookiesInterceptor: AddCookiesInterceptor

    fun initialize(context: Context) {
        cookieInterceptor = ReceivedCookiesInterceptor(context)
        addCookiesInterceptor = AddCookiesInterceptor(context)

    }
    private const val BASE_URL = "https://evanescent-beautiful-venus.glitch.me/"
    //
    class RetryInterceptor(private val maxRetries: Int, private val backoff: Long) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var response = chain.proceed(chain.request())
            var tryCount = 0

            while (!response.isSuccessful && tryCount < maxRetries) {
                response.close() // Close the previous response before retrying
                tryCount++
                Thread.sleep(backoff * tryCount) // This is not recommended on a network thread; consider a different approach
                response = chain.proceed(chain.request())
            }
            return response
        }
    }

    val apiService: ApiService by lazy {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(cookieInterceptor)
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(RetryInterceptor(3, 2000)) // Retries up to 3 times with exponential backoff
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        Log.d("Received Cookie interceptor", cookieInterceptor.toString())
        Log.d("Add Cookie interceptor", addCookiesInterceptor.toString())
        Log.d("client builder", clientBuilder.toString())

        val okHttpClient = clientBuilder.build()
        val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        retrofit.create(ApiService::class.java)
    }
}