package com.bm.docathome.patient.common

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.bm.docathome.patient.data.remote.AuthApi
import javax.inject.Inject

class TokenIdManager @Inject constructor(
    private val prefs: SharedPreferences
) {

    companion object {
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_ID = "id"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
    }

    fun storeTokens(accessToken: String, id: String) {
        prefs.edit {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_ID, id)
        }
    }
    fun getToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getId(): String? {
        return prefs.getString(KEY_ID, null)
    }

    fun isRefreshToken():Boolean{
        val refreshToken = prefs.getStringSet(KEY_REFRESH_TOKEN, null)
        Log.d("Available", "${refreshToken}")
        return !refreshToken.isNullOrEmpty()
    }

    fun clearTokens() {
        prefs.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_ID)
            remove(KEY_REFRESH_TOKEN)
        }
    }
}