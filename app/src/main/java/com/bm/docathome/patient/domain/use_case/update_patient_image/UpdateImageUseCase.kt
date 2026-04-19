package com.bm.docathome.patient.domain.use_case.update_patient_image

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UpdateImageUseCase@Inject constructor(
    private val patientRepository: PatientRepository
)  {
    operator fun invoke(patientId:String,file: File): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            patientRepository.updateImage(patientId, file = MultipartBody.Part.createFormData(
                "file",
                filename = file.name,
                file.asRequestBody()
            ))
            emit(Resource.Success(Unit))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}