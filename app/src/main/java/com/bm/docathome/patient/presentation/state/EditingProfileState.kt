package com.bm.docathome.patient.presentation.state

data class EditingProfileState(
    val first_name: String="",
    val first_nameError: String? = null,
    val isLoading: Boolean = false,
    val last_name: String = "",
    val last_nameError: String? =null,
    val phone_number: String = "",
    val phone_numberError: String? =null
)
