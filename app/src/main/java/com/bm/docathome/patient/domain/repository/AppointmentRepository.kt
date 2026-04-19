package com.bm.docathome.patient.domain.repository

import com.bm.docathome.patient.data.remote.dto.AppointmentDto
import com.bm.docathome.patient.domain.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointments(idP: String):List<AppointmentDto>
    suspend fun createAppointment(idP: String,appointment: Appointment):AppointmentDto
}