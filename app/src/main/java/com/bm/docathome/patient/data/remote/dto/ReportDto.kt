package com.bm.docathome.patient.data.remote.dto

import com.bm.docathome.patient.domain.model.Report
import java.time.LocalDate

data class ReportDto(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val doctor: String,
    val patient: String,
    val report_content: String
){
    fun toReport():Report{
        return Report(
            _id,
            LocalDate.parse(createdAt.subSequence(0,10))
        )
    }
}