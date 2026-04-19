package com.bm.docathome.patient.domain.use_case.RegistrationForm

import android.util.Log
import java.time.LocalDate

class ValidateBirthDate {
    fun execute(birth_date: LocalDate) : ValidtationResult{
        Log.d("BirthDate","$birth_date")
        if (birth_date.isAfter(LocalDate.now()) || birth_date.isEqual(LocalDate.now()) )
            return ValidtationResult(
                successful = false,
                errorMessage = "this is invalid birth date"
            )

        return ValidtationResult(successful = true)
    }
}