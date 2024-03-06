package com.app.eazyliving.network.Cookies

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Base64


    @RequiresApi(Build.VERSION_CODES.O)
    fun decodeJWT(token: String): Pair<String, String>{
        val elements = token.split('.')
        if (elements.size == 3) {
            var (header, payload, signature) = elements
            val decoder: Base64.Decoder = Base64.getUrlDecoder()

             header= String(decoder.decode(elements[0]))
            Log.d("header","$header")
             payload = String(decoder.decode(elements[1]))
            Log.d("payload","$[payload]")
            return Pair(header, payload)
        } else {
            error("Invalid token")
        }
    }




