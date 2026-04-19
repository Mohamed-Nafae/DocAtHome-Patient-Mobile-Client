package com.bm.docathome.patient.data.remote

import com.bm.docathome.patient.data.remote.dto.PatientDto
import com.bm.docathome.patient.data.remote.dto.PatientUpdate
import com.bm.docathome.patient.data.remote.dto.ReportDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface PatientAPI {
    //get one patient by id
    @GET("{id}")
    suspend fun getPatient (
        @Path("id") id: String
    ) : PatientDto

    //update one patient by id
    @PUT("{id}")
    suspend fun updatePatient(
        @Path("id") id: String,
        @Body patient: PatientUpdate
    ):PatientDto

    // update one image by patient id
    @Multipart
    @PUT("{id}/image")
    suspend fun updateImage(
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ):PatientDto

    // create Patient
    @Multipart
    @POST("register")
    suspend fun signUp(
        @Part data: MultipartBody.Part,
        @Part file: MultipartBody.Part
    ):PatientDto

    //display one image by patient id
    @Streaming
    @GET("{id}/image")
    suspend fun displayImage(
        @Path("id") id: String,
    ):Response<ResponseBody>

    //Download one medical folder by patient id
    @Streaming
    @GET("{idP}/medicalFolder")
    suspend fun downloadMedicalFolder(
        @Path("idP") idP: String
    ):Response<ResponseBody>

    // get all reports in patient id
    @GET("{idP}/reports")
    suspend fun getReports(
        @Path("idP") idP: String
    ):List<ReportDto>

    // download one report id
    @GET("{idP}/reports/{id}")
    suspend fun downloadReport(
        @Path("idP") idP: String,
        @Path("id") id:String
    ):Response<ResponseBody>
}