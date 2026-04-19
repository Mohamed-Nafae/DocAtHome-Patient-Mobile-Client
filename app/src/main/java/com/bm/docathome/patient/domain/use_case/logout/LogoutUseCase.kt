package com.bm.docathome.patient.domain.use_case.logout

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenIdManager: TokenIdManager
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.logout()
            tokenIdManager.clearTokens()
            emit(Resource.Success(Unit))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}