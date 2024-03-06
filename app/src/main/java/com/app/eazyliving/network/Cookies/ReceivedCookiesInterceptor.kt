package com.app.eazyliving.network.Cookies

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val sharedPreferences = context.getSharedPreferences("MyCookiePreferences", Context.MODE_PRIVATE)
            val cookies = sharedPreferences.getStringSet("PREF_COOKIES", HashSet()) as HashSet<String>

            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            val editor = sharedPreferences.edit()
            editor.putStringSet("PREF_COOKIES", cookies).apply()
            editor.commit()


            // Log the cookies for debugging purposes
            Log.d("ReceivedCookiesInterceptor", "Stored cookies: $cookies")
        }

        return originalResponse
    }
}
