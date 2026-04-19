package com.bm.docathome.patient.domain.repository


import com.bm.docathome.patient.data.remote.dto.PatientUpdate
import com.bm.docathome.patient.data.remote.dto.PatientDto
import com.bm.docathome.patient.data.remote.dto.ReportDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

interface PatientRepository {
    suspend fun getPatient(id: String):PatientDto
    suspend fun updatePatien(id: String,patient: PatientUpdate):PatientDto
    suspend fun displayImage(id: String):Response<ResponseBody>
    suspend fun updateImage(id: String,file:MultipartBody.Part):PatientDto
    suspend fun downloadMedicalFolder(id: String):Response<ResponseBody>
    suspend fun getReports(id: String):List<ReportDto>
    suspend fun downloadReport(idP:String,id:String):Response<ResponseBody>
    suspend fun singUp(data:MultipartBody.Part,file: MultipartBody.Part):PatientDto
}