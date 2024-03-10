package com.app.eazyliving.network.Cookies

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

object TokenManager {

    fun getDecodedTokenData(context: Context): Pair<String, String>? {
        // Check Android version inside the function
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val jwtToken = extractJwtFromCookies(context) ?: return null
            return decodeJWT(jwtToken)
        } else {
            Log.e("TokenManager", "Android version is too low for Base64 decoding")
            return null
        }
    }
}
