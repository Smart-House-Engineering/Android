package com.app.eazyliving.network.Cookies


import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        // Retrieve the cookies from SharedPreferences
        val sharedPreferences = context.getSharedPreferences("MyCookiePreferences", Context.MODE_PRIVATE)
        val cookies = sharedPreferences.getStringSet("PREF_COOKIES", HashSet()) ?: HashSet()

        // Add the cookies to the request header
        for (cookie in cookies) {
            builder.addHeader("Cookie", cookie)
        }

        return chain.proceed(builder.build())
    }
}
