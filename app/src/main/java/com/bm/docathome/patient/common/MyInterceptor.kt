package com.bm.docathome.patient.common

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Header
import javax.inject.Inject

class MyInterceptor @Inject constructor(
    private val tokenIdManager: TokenIdManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = if (tokenIdManager.getToken() == null) "" else tokenIdManager.getToken()
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}