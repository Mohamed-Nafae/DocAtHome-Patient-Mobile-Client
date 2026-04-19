package com.bm.docathome.patient.data.remote

import com.bm.docathome.patient.data.remote.dto.AuthResponse
import com.bm.docathome.patient.domain.model.AuthRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    //get one patient by id
    @POST("login")
    suspend fun singIn(
        @Body request: AuthRequest
    ): AuthResponse



    @GET("token")
    suspend fun getToken(): AuthResponse


    @GET("logout")
    suspend fun logout():Response<Unit>
}