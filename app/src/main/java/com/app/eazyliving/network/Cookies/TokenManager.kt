package com.app.eazyliving.network.Cookies

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

object TokenManager {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDecodedTokenData(context: Context): JwtPayload? {
        val jwtToken = extractJwtFromCookies(context) ?: return null
        return decodeJWTAndExtractData(jwtToken) // This now returns JwtPayload or null
    }
}
