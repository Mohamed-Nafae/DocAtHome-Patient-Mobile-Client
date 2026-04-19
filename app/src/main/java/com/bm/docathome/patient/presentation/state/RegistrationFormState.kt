package com.bm.docathome.patient.presentation.state

import java.io.File
import java.time.LocalDate

data class RegistrationFormState(
    val birth_date: LocalDate = LocalDate.now(),
    val birth_dateError: String? = null,
    val email_address: String="",
    val emailError: String?=null,
    val first_name: String="",
    val first_nameError: String? = null,
    val gender: String = "Male",
    val address: String = "",
    val addressError: String? =null,
    val city: String = "",
    val cityError: String? =null,
    val country: String ="",
    val countryError: String? =null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val last_name: String = "",
    val last_nameError: String? =null,
    val medical_folder: File ?= null,
    val medical_folderError: String? =null,
    val password: String = "",
    val passwordError: String? =null,
    val repeatedPassword:String ="",
    val repeatedPasswordError: String? =null,
    val phone_number: String = "",
    val phone_numberError: String? =null,
    val isLoading: Boolean = false,
    val error: String = "",
)
