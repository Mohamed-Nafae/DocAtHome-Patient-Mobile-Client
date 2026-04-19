package com.bm.docathome.patient.data.repository

import com.bm.docathome.patient.data.remote.AppointmentApi
import com.bm.docathome.patient.data.remote.dto.AppointmentDto
import com.bm.docathome.patient.domain.model.Appointment
import com.bm.docathome.patient.domain.repository.AppointmentRepository
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
   private val appointmentApi: AppointmentApi
):AppointmentRepository {
    override suspend fun getAppointments(idP: String): List<AppointmentDto> {
        return appointmentApi.getAppointments(idP)
    }

    override suspend fun createAppointment(idP: String,appointment: Appointment): AppointmentDto {
        return appointmentApi.createAppointment(idP,appointment)
    }
}