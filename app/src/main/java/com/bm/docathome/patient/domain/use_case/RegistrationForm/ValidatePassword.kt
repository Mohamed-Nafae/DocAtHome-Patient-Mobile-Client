package com.bm.docathome.patient.domain.use_case.RegistrationForm



class ValidatePassword {
    fun execute(password: String) : ValidtationResult{
        if (password.length < 8)
            return ValidtationResult(
                successful = false,
                errorMessage = "The password needs consist of at least 8 characters"
            )
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits)
            return ValidtationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        return ValidtationResult(successful = true)
    }
}