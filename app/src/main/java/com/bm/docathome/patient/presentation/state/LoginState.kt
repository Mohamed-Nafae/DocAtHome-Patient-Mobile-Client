package com.bm.docathome.patient.presentation.state

import com.bm.docathome.patient.data.remote.dto.AuthResponse


data class LoginState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val email_address: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)

