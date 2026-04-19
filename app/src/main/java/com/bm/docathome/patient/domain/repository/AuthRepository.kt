package com.bm.docathome.patient.domain.repository

import com.bm.docathome.patient.data.remote.dto.AuthResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun  signIn( email: String , password : String): AuthResponse
    suspend fun getToken():AuthResponse
    suspend fun logout():Response<Unit>
}