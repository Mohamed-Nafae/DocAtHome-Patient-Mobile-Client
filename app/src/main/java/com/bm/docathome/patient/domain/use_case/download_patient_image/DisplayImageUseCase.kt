package com.bm.docathome.patient.domain.use_case.download_patient_image

import android.graphics.Bitmap
import android.net.Uri
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class DisplayImageUseCase @Inject constructor(
    private val patientRepository: PatientRepository
)  {
    operator fun invoke(patientId:String): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            emit(Resource.Loading())
            val result = patientRepository.displayImage(patientId)
            emit(Resource.Success(result))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}