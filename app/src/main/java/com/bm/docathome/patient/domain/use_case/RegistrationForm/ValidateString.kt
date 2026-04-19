package com.bm.docathome.patient.domain.use_case.RegistrationForm

class ValidateString {
    fun execute(string:String, stringName: String) : ValidtationResult{
      if (string.isBlank()){
          return ValidtationResult(
              successful = false,
              errorMessage = "The $stringName can't be blank."
          )
      }
        return ValidtationResult(true)
    }
}