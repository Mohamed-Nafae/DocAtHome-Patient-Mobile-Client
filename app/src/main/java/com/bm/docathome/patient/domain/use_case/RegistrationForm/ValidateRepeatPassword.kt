package com.bm.docathome.patient.domain.use_case.RegistrationForm

class ValidateRepeatPassword {
    fun execute(password: String,repeatedPassword:String) : ValidtationResult{
        if (password != repeatedPassword)
            return ValidtationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )

        return ValidtationResult(successful = true)
    }
}