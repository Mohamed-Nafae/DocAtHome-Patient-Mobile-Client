package com.bm.docathome.patient.domain.model

data class Location(
    val address: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
