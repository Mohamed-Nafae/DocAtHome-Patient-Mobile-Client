package com.bm.docathome.patient.presentation.state

import com.bm.docathome.patient.domain.model.Patient

data class PatientState (
    val isLoading : Boolean = false,
    val patient: Patient ?=null,
    val error: String = "",
)