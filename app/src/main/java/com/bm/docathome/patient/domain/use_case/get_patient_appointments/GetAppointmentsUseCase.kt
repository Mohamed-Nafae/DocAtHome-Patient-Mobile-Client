package com.bm.docathome.patient.domain.use_case.get_patient_appointments

import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.model.Appointment
import com.bm.docathome.patient.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppointmentsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    operator fun invoke(patientId:String): Flow<Resource<List<Appointment>>> = flow {
        try {
            emit(Resource.Loading())
            val appointments = appointmentRepository.getAppointments(patientId).map {
                it.toAppointment()
            }
            emit(Resource.Success(appointments))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}