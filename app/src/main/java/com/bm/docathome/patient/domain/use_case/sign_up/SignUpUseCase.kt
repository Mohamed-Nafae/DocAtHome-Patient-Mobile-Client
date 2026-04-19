package com.bm.docathome.patient.domain.use_case.sign_up

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.model.Patient
import com.bm.docathome.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val patientRepository: PatientRepository
) {
    operator fun invoke(patient: Patient, file: File): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val patientRequestBody =
                patient.toJson().toRequestBody("application/json".toMediaTypeOrNull())
            patientRepository.singUp(
                data = MultipartBody.Part.createFormData(
                    name = "data",
                    null,
                    patientRequestBody
                ),
                file = MultipartBody.Part.createFormData(
                    "file",
                    filename = file.name,
                    file.asRequestBody("application/pdf".toMediaTypeOrNull())
                )
            )
            emit(Resource.Success(Unit))
        } catch (e: HttpException) {
            if(e.code() == 409) emit(Resource.Error("This patient already exist!"))
            else emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}