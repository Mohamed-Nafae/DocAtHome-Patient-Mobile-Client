package com.bm.docathome.patient.domain.use_case.RegistrationForm

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String) : ValidtationResult{
        if (!ValidateString().execute(email,"email").successful)
            return ValidateString().execute(email,"email")

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidtationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        return ValidtationResult(successful = true)
    }
}