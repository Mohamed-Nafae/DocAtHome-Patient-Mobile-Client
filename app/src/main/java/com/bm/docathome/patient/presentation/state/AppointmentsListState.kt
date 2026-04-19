package com.bm.docathome.patient.presentation.state

import com.bm.docathome.patient.domain.model.Appointment

data class AppointmentsListState(
    val isLoading: Boolean = false,
    val appointments: List<Appointment> = emptyList(),
    val error:String  =""
)
