package com.bm.docathome.patient.domain.use_case.get_patient_reports

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.model.Report
import com.bm.docathome.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(
    private val patientRepository: PatientRepository
) {
    operator fun invoke(patientId:String): Flow<Resource<List<Report>>> = flow {
        try {
            emit(Resource.Loading())
            val reports = patientRepository.getReports(patientId).map {
                it.toReport()
            }
            emit(Resource.Success(reports))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}