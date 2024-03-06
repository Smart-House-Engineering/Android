package com.app.eazyliving.network

import android.content.Context
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
    private var cookieInterceptor: ReceivedCookiesInterceptor? = null
    fun initialize(context: Context) {
        cookieInterceptor = ReceivedCookiesInterceptor(context)
    }
    private const val BASE_URL = "https://backend-ten-ruby.vercel.app/"
    val apiService: ApiService by lazy {
        val clientBuilder = OkHttpClient.Builder()
        cookieInterceptor?.let {
            clientBuilder.addInterceptor(it)
        }
        val okHttpClient = clientBuilder.build()
        val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        retrofit.create(ApiService::class.java)
    }
}