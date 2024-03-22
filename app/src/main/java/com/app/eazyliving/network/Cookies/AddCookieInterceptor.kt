package com.app.eazyliving.network.Cookies

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Retrieve the stored cookies
        val sharedPreferences = context.getSharedPreferences("MyCookiePreferences", Context.MODE_PRIVATE)
        val cookies = sharedPreferences.getStringSet("PREF_COOKIES", null) ?: emptySet()

        // Find the specific cookie named "SmartHouseToken"
        val smartHouseToken = cookies.firstOrNull { it.startsWith("SmartHouseToken=") }
        if (smartHouseToken != null) {
            // Add it to the request header
            requestBuilder.addHeader("Cookie", smartHouseToken)
            Log.d("AddCookiesInterceptor", "Adding SmartHouseToken to request: $smartHouseToken")
        } else {
            Log.d("AddCookiesInterceptor", "No SmartHouseToken found")
        }

        return chain.proceed(requestBuilder.build())
    }
}
