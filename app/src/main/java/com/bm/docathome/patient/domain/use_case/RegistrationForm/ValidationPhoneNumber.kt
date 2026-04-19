package com.bm.docathome.patient.domain.use_case.RegistrationForm

import android.util.Patterns

class ValidationPhoneNumber {
    fun execute(phone_number: String): ValidtationResult {
        if (!ValidateString().execute(phone_number, "phone number").successful)
            return ValidateString().execute(phone_number, "phone number")

        if ((!Patterns.PHONE.matcher(phone_number)
                .matches()) || phone_number.trim().length != 10 || phone_number[0] != '0'
        ) {
            return ValidtationResult(
                successful = false,
                errorMessage = "That's not a valid phone number"
            )
        }

        return ValidtationResult(successful = true)
    }
}