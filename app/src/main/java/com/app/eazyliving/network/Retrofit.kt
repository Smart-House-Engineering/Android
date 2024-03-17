package com.app.eazyliving.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.app.eazyliving.network.Cookies.AddCookiesInterceptor
import com.app.eazyliving.network.Cookies.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

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
    val apiService: ApiService by lazy {
        val clientBuilder = OkHttpClient.Builder()
        cookieInterceptor?.let {
            clientBuilder.addInterceptor(it)
        }
        addCookiesInterceptor?.let {
            clientBuilder.addInterceptor(it)
        }
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