package com.app.eazyliving.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.app.eazyliving.network.Cookies.AddCookiesInterceptor
import com.app.eazyliving.network.Cookies.ReceivedCookiesInterceptor
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.pow

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


@SuppressLint("StaticFieldLeak")
object Retrofit {

    private lateinit var context: Context
    private lateinit var cookieInterceptor: ReceivedCookiesInterceptor
    private lateinit var addCookiesInterceptor: AddCookiesInterceptor

    fun initialize(context: Context) {
        this.context = context
        cookieInterceptor = ReceivedCookiesInterceptor(context)
        addCookiesInterceptor = AddCookiesInterceptor(context)

    }
    private const val BASE_URL = "https://evanescent-beautiful-venus.glitch.me/"

    class TokenInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            if (request.url.encodedPath.contains("/auth/login")) {
                return chain.proceed(request)
            }
            if (!hasValidCookie()) {
                // Here you can throw an exception, or handle it by returning a dummy response
                // For instance, create a response with a 401 Unauthorized status.
                return chain.proceed(chain.request().newBuilder().build()).newBuilder()
                    .code(401)
                    .message("Unauthorized: cookie missing or invalid")
                    .protocol(chain.connection()?.protocol() ?: Protocol.HTTP_1_1)
                    .request(chain.request())
                    .body("{\"error\":\"Unauthorized\"}".toResponseBody("application/json".toMediaTypeOrNull()))
                    .build()
            }
            return chain.proceed(chain.request())
        }

        private fun hasValidCookie(): Boolean {
            val sharedPreferences = context.getSharedPreferences("MyCookiePreferences", Context.MODE_PRIVATE)
            val cookies = sharedPreferences.getStringSet("PREF_COOKIES", null)
            return cookies?.any { it.startsWith("SmartHouseToken=") } ?: false
        }
    }


class RetryInterceptor(private val maxRetries: Int, private val backoff: Long) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response: Response
        var tryCount = 0

        while (true) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful) {
                    return response
                }
                response.close()
            } catch (e: IOException) { // Catch network-related exceptions here
                if (tryCount >= maxRetries) throw e
            }

            val nextDelay = 2.0.pow(tryCount.toDouble()).toLong() * backoff
            tryCount++
            Thread.sleep(nextDelay)
            request = request.newBuilder().build() // Rebuild the request if needed
        }
    }
}

    val apiService: ApiService by lazy {
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(TokenInterceptor(context))
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