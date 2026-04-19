package com.bm.docathome.patient.data.remote.dto

import com.bm.docathome.patient.domain.model.Location
import com.bm.docathome.patient.domain.model.Patient
import java.time.LocalDate


data class PatientDto(
    val __v: Int,
    val _id: String,
    val appointments: List<String>?=null,
    val birth_date: String,
    val createdAt: String,
    val email_address: String,
    val first_name: String,
    val gender: String,
    val image: String?=null,
    val last_name: String,
    val location: Location,
    val medical_folder: String,
    val medical_reports: List<String>?=null,
    val password: String?=null,
    val phone_number: String
) {
    fun toPatient(): Patient {
        return Patient(
            appointments,
            birth_date = LocalDate.parse(birth_date),
            email_address,
            first_name,
            gender,
            null,
            last_name,
            location,
            medical_folder,
            medical_reports,
            password,
            phone_number
        )
    }
}

