package com.bm.docathome.patient.domain.model

import android.graphics.Bitmap
import com.google.gson.Gson
import java.time.LocalDate

data class Patient(
    val appointments: List<String>?=null,
    val birth_date: LocalDate,
    val email_address: String,
    val first_name: String,
    val gender: String,
    val imageBitmap: Bitmap?=null,
    val last_name: String,
    val location: Location,
    val medical_folder: String,
    val medical_reports: List<String>?=null,
    val password: String?=null,
    val phone_number: String
){
    fun toJson(): String {
        val gson = Gson()
        val patientCreated = PatientCreated(
            this.appointments,
            this.birth_date.toString(),
            this.email_address,
            this.first_name,
            this.gender,
            this.imageBitmap,
            this.last_name,
            this.location,
            this.medical_folder,
            this.medical_reports,
            this.password,
            this.phone_number
        )
        return gson.toJson(patientCreated)
    }
}


