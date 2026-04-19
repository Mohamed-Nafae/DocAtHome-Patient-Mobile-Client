package com.bm.docathome.patient.domain.model

import android.graphics.Bitmap

data class PatientCreated(
    val appointments: List<String>?=null,
    val birth_date: String,
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
)