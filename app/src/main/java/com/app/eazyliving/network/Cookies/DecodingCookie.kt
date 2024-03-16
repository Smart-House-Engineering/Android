package com.app.eazyliving.network.Cookies

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.app.eazyliving.model.User
import org.json.JSONObject
import java.util.Base64


data class JwtPayload(val email: String, val role: String)

@RequiresApi(Build.VERSION_CODES.O)
fun decodeJWTAndExtractData(token: String): JwtPayload? {
    val elements = token.split('.')
    if (elements.size == 3) {
        val payload = elements[1] // The payload is the second part of the token
        val decoder = Base64.getUrlDecoder() // Decoding from Base64URL
        val decodedPayload = String(decoder.decode(payload))
        Log.d("JWT", "Decoded payload: $decodedPayload")

        // Convert payload into a JSONObject
        val payloadObj = JSONObject(decodedPayload)

        // Extract email and role
        val userObj =
            payloadObj.optJSONObject("user") // 'user' is expected to be a nested JSON object
        if (userObj != null) {
            val email = userObj.optString("email", "")
            val role = userObj.optString("role", "")
            Log.d("Role", "Decoded payload: $role")
            return JwtPayload(email, role)
        } else {
            Log.e("JWT", "Invalid token: does not contain 3 segments.")
            return null
        }
    }else{
        return null
    }
}





