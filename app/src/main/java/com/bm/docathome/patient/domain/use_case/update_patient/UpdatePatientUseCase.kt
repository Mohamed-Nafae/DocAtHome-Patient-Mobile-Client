package com.bm.docathome.patient.domain.use_case.update_patient

import com.bm.docathome.patient.data.remote.dto.PatientUpdate
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.model.Patient
import com.bm.docathome.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdatePatientUseCase @Inject constructor(
    private val patientRepository: PatientRepository
)  {
    operator fun invoke(patientId:String,patient: PatientUpdate): Flow<Resource<Patient>> = flow {
        try {
            emit(Resource.Loading())
            val result = patientRepository.updatePatien(patientId, patient).toPatient()
            emit(Resource.Success(result))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}