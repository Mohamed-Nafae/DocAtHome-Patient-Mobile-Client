package com.bm.docathome.patient.domain.use_case.download_patient_medicalFolder

import android.content.Context
import android.os.Environment
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.repository.PatientRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class DownloadMedicalFolderUseCase @Inject constructor(
    private val patientRepository: PatientRepository
)  {
    operator fun invoke(patientId: String): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            val response = patientRepository.downloadMedicalFolder(patientId)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error("Empty response body"))
                }
            } else {
                emit(Resource.Error("Download request failed"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}