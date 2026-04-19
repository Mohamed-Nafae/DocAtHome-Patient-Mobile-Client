package com.bm.docathome.patient.domain.model

import java.time.LocalDate

data class Appointment(
    val _id: String,
    val careTaker: Boolean,
    val date: LocalDate?=null,
    val location: Location,
    val qrCode_id: String,
    val status: String?=null,
    val typeofAppointment: String
)
