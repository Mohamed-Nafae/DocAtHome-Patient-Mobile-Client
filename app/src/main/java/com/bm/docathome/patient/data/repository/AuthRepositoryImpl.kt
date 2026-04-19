package com.bm.docathome.patient.data.repository

import com.bm.docathome.patient.data.remote.AuthApi
import com.bm.docathome.patient.data.remote.dto.AuthResponse
import com.bm.docathome.patient.domain.model.AuthRequest
import com.bm.docathome.patient.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
):AuthRepository{
    override suspend fun signIn(email: String, password: String): AuthResponse {
        return api.singIn(AuthRequest(email,password))
    }

    override suspend fun getToken(): AuthResponse {
        return api.getToken()
    }

    override suspend fun logout(): Response<Unit> {
        return api.logout()
    }

}