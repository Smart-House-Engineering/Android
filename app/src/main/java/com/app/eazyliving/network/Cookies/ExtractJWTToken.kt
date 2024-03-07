package com.app.eazyliving.network.Cookies

import android.content.Context

fun extractJwtFromCookies(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("MyCookiePreferences", Context.MODE_PRIVATE)
    val cookies = sharedPreferences.getStringSet("PREF_COOKIES", null) ?: return null

    // Assuming the JWT token is stored as a cookie named "AuthToken"
    val jwtCookie = cookies.firstOrNull { it.startsWith("SmartHouseToken=") }
    return jwtCookie?.substringAfter("SmartHouseToken=")
}
