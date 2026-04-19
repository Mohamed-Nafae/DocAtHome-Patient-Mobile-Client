package com.bm.docathome.patient.data.remote.dto

import com.bm.docathome.patient.domain.model.Appointment
import com.bm.docathome.patient.domain.model.Location
import java.time.LocalDate

data class AppointmentDto(
    val __v: Int,
    val _id: String,
    val careTaker: Boolean,
    val createdAt: String,
    val date: String?=null,
    val doctor: String?=null,
    val location: Location,
    val nurse: String?=null,
    val patient: String,
    val qrCode_id: String,
    val status: String?=null,
    val typeofAppointment: String
){
    fun toAppointment(): Appointment {
        return Appointment(
            _id,
            careTaker,
            date = LocalDate.parse(date),
            location,
            qrCode_id,
            status,
            typeofAppointment
        )
    }
}

