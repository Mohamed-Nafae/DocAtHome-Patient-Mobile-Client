package com.bm.docathome.patient.domain.use_case.RegistrationForm

data class ValidtationResult(
    val successful:Boolean,
    val errorMessage: String ?= null
)
