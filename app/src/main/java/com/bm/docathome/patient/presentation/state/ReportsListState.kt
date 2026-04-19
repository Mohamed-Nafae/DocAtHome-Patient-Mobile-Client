package com.bm.docathome.patient.presentation.state

import com.bm.docathome.patient.domain.model.Report

data class ReportsListState (
    val isLoading: Boolean = false,
    val reports: List<Report> = emptyList(),
    val error:String  =""
)