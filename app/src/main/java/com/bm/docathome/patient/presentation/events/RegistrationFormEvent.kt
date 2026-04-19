package com.bm.docathome.patient.presentation.events

import java.io.File
import java.time.LocalDate

sealed class RegistrationFormEvent{
    data class EmailChanged(val email:String): RegistrationFormEvent()
    data class BirthDateChanged(val birth_date:LocalDate): RegistrationFormEvent()
    data class FirstNameChanged(val first_name:String): RegistrationFormEvent()
    data class GenderChanged(val gender: String): RegistrationFormEvent()
    data class AddressChanged(val address:String): RegistrationFormEvent()
    data class CityChanged(val city:String): RegistrationFormEvent()
    data class CountryChanged(val country:String): RegistrationFormEvent()
    data class LastNameChanged(val last_name:String): RegistrationFormEvent()
    data class PasswordChanged(val password:String): RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeated_password:String): RegistrationFormEvent()
    data class PhoneNumberChanged(val phone_number:String): RegistrationFormEvent()
    data class MedicalFolderChanged(val medical_folder:File):RegistrationFormEvent()
    data class LocationChanged(val latitude: Double,val longitude: Double): RegistrationFormEvent()

    object SignUp: RegistrationFormEvent()
    object SignIn: RegistrationFormEvent()
    object Confirm: RegistrationFormEvent()
    object Cancel:RegistrationFormEvent()
    object Logout:RegistrationFormEvent()

}
