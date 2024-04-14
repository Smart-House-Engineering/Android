package com.app.eazyliving.components

import android.content.Context
import android.util.Log
import com.app.eazyliving.network.Retrofit



class SessionRepository(private val context: Context) {

    private fun clearSessionData() {
        val sharedPreferences = context.getSharedPreferences("YourPrefsName", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    suspend fun logout(): Boolean {
        return try {
            val response = Retrofit.apiService.logout()
            if (response.isSuccessful) {
                Log.d("Logout", "Logout successful")
                clearSessionData()
                true
            } else {
                Log.d("Logout", "Logout failed: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("Logout", "Error during logout", e)
            false
        }
    }
}
