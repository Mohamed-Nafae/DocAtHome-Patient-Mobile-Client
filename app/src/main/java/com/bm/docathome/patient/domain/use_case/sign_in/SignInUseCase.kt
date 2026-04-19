package com.bm.docathome.patient.domain.use_case.sign_in

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.data.remote.dto.AuthResponse
import com.bm.docathome.patient.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenIdManager: TokenIdManager
) {
    operator fun invoke(email: String, password: String): Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepository.signIn(email, password)
            tokenIdManager.storeTokens(response.accessToken,response.id)
            emit(Resource.Success(response))
        }catch (e: HttpException){
            if(e.code() == 401) emit(Resource.Error("incorrect email or password"))
            else emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}