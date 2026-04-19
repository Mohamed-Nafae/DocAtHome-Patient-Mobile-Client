package com.bm.docathome.patient.common

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Inject

class MyCookieJar @Inject constructor(
    private val prefs: SharedPreferences
) : CookieJar {

    init {
        // Install a CookieManager as the default CookieHandler
        CookieHandler.setDefault(CookieManager(null, CookiePolicy.ACCEPT_ALL))
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val cookieStrings = cookies.map { it.toString() }
        prefs.edit().putStringSet(Constants.KEY_REFRESH_TOKEN, cookieStrings.toSet()).apply()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookieStrings = prefs.getStringSet(Constants.KEY_REFRESH_TOKEN, null)
        return cookieStrings?.map { Cookie.parse(url,it)!! } ?: emptyList()
    }
}
