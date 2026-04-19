package com.bm.docathome.patient.common

import com.bm.docathome.patient.common.Constants.BASE_URL
import com.bm.docathome.patient.data.remote.AppointmentApi
import com.bm.docathome.patient.data.remote.AuthApi
import com.bm.docathome.patient.data.remote.PatientAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitInstance @Inject constructor(
    private val myInterceptor: MyInterceptor,
    private val cookieJar: MyCookieJar
) {


    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)

    private val client by lazy {
        OkHttpClient.Builder().apply {
            addInterceptor(myInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    private val authClient by lazy {
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            cookieJar(cookieJar)
        }.build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val patientApi: PatientAPI by lazy {
        retrofit.create(PatientAPI::class.java)
    }

    val appointmentApi:AppointmentApi by lazy {
        retrofit.create(AppointmentApi::class.java)
    }


    val authApi: AuthApi by lazy {
        retrofit.newBuilder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}