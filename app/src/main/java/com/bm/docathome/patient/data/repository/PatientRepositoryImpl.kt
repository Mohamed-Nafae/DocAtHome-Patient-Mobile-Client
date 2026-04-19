package com.bm.docathome.patient.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.bm.docathome.patient.data.remote.dto.PatientUpdate
import com.bm.docathome.patient.data.remote.PatientAPI
import com.bm.docathome.patient.data.remote.dto.PatientDto
import com.bm.docathome.patient.data.remote.dto.ReportDto
import com.bm.docathome.patient.domain.repository.PatientRepository
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val patientAPI: PatientAPI
):PatientRepository {
    override suspend fun getPatient( id: String): PatientDto {
        return patientAPI.getPatient( id = id)
    }

    override suspend fun updatePatien(id: String, patient: PatientUpdate): PatientDto {
        return patientAPI.updatePatient(id = id, patient = patient)
    }

    override suspend fun displayImage(id: String): Response<ResponseBody> {
        return patientAPI.displayImage(id)
    }

    override suspend fun updateImage(id: String, file: MultipartBody.Part):PatientDto {
        return patientAPI.updateImage(id,file)
    }

    override suspend fun downloadMedicalFolder(id: String): Response<ResponseBody> {
        return patientAPI.downloadMedicalFolder(id)
    }

    override suspend fun getReports(id: String): List<ReportDto> {
        return patientAPI.getReports(id)
    }

    override suspend fun downloadReport(idP: String, id: String): Response<ResponseBody> {
        return patientAPI.downloadReport(idP,id)
    }

    override suspend fun singUp(data: MultipartBody.Part, file: MultipartBody.Part): PatientDto {
        return patientAPI.signUp(data,file)
    }
}