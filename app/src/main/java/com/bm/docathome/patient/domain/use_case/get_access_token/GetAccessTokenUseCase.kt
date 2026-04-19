package com.bm.docathome.patient.domain.use_case.get_access_token

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.data.remote.dto.AuthResponse
import com.bm.docathome.patient.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenIdManager: TokenIdManager
) {
    operator fun invoke():Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
            val authResponse = authRepository.getToken()
            tokenIdManager.storeTokens(authResponse.accessToken,authResponse.id)
            emit(Resource.Success(authResponse))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }

    }
}