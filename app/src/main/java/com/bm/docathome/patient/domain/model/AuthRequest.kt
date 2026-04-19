package com.bm.docathome.patient.domain.model

data class AuthRequest(
    val email: String,
    val password: String
)