package com.bm.docathome.patient.domain.use_case.download_patient_report

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class DownloadReportUseCase @Inject constructor(
    private val patientRepository: PatientRepository
)  {
    operator fun invoke(patientId: String,reportId:String): Flow<Resource<Response<ResponseBody>>> = flow {
        try {
            val response = patientRepository.downloadReport(patientId,reportId)

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