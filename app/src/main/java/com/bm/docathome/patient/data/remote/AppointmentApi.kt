package com.bm.docathome.patient.data.remote

import com.bm.docathome.patient.data.remote.dto.AppointmentDto
import com.bm.docathome.patient.domain.model.Appointment
import retrofit2.http.*

interface AppointmentApi {
    // get all appointments by patient id
    @GET("{idP}/appointments")
    suspend fun getAppointments(
        @Path("idP") idP: String
    ):List<AppointmentDto>

    // create one appointment by patient id
    @POST("{idP}/appointments")
    suspend fun createAppointment(
        @Path("idP") idP: String,
        @Body appointment: Appointment
    ):AppointmentDto

    //get one appointments by id
    @GET("{idP}/appointments/{id}")
    suspend fun getOneAppointment(
        @Path("idP") idP: String,
        @Path("id") id: String
    ):AppointmentDto

    //update one appointment by id
    @PUT("{idP}/appointments/{id}")
    suspend fun updateOneAppointment(
        @Path("idP") idP: String,
        @Path("id") id: String
    ):AppointmentDto

    // delete one appointment by id
    @DELETE("{idP}/appointments/{id}")
    suspend fun deleteOneAppointment(
        @Path("idP") idP: String,
        @Path("id") id: String
    ):AppointmentDto

}