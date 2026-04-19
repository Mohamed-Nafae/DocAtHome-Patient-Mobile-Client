package com.bm.docathome.patient.presentation.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.domain.model.Location
import com.bm.docathome.patient.domain.model.Patient
import com.bm.docathome.patient.domain.use_case.RegistrationForm.*
import com.bm.docathome.patient.domain.use_case.sign_in.SignInUseCase
import com.bm.docathome.patient.domain.use_case.sign_up.SignUpUseCase
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.state.LoginState
import com.bm.docathome.patient.presentation.state.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())
    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val validateBirthDate: ValidateBirthDate = ValidateBirthDate()
    private val validateRepeatPassword: ValidateRepeatPassword = ValidateRepeatPassword()
    private val validationPhoneNumber: ValidationPhoneNumber = ValidationPhoneNumber()
    private val validateString: ValidateString = ValidateString()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.PhoneNumberChanged -> {
                state = state.copy(phone_number = event.phone_number)
            }
            is RegistrationFormEvent.BirthDateChanged -> {
                state = state.copy(birth_date = event.birth_date)
            }
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email_address = event.email)
            }
            is RegistrationFormEvent.FirstNameChanged -> {
                state = state.copy(first_name = event.first_name)
            }
            is RegistrationFormEvent.GenderChanged -> {
                state = state.copy(gender = event.gender)
            }
            is RegistrationFormEvent.LastNameChanged -> {
                state = state.copy(last_name = event.last_name)
            }
            is RegistrationFormEvent.LocationChanged -> {
                state = state.copy(latitude = event.latitude, longitude = event.longitude)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeated_password)
            }
            is RegistrationFormEvent.AddressChanged -> {
                state = state.copy(address = event.address)
            }
            is RegistrationFormEvent.CityChanged -> {
                state = state.copy(city = event.city)
            }
            is RegistrationFormEvent.CountryChanged -> {
                state = state.copy(country = event.country)
            }
            is RegistrationFormEvent.MedicalFolderChanged -> {
                state = state.copy(medical_folder = event.medical_folder)
            }

            is RegistrationFormEvent.SignUp -> {
                submitData()
            }

        }
    }

    private fun submitData() {

        val emailResult = validateEmail.execute(state.email_address)
        val passwordResult = validatePassword.execute(state.password)
        val repeatPasswordResult = validateRepeatPassword.execute(
            password = state.password,
            repeatedPassword = state.repeatedPassword
        )
        val phoneNumberResult = validationPhoneNumber.execute(state.phone_number)
        val birthDateResult = validateBirthDate.execute(state.birth_date)
        val firstNameResult = validateString.execute(string = state.first_name, stringName = "first name")
        val lastNameResult = validateString.execute(string = state.last_name, stringName = "last name")
        val addressResult = validateString.execute(string = state.address, stringName ="address" )
        val cityResult = validateString.execute(string = state.city, stringName = "city")
        val countryResult = validateString.execute(string = state.country, stringName = "country")
        val medicalFolderResult = if(state.medical_folder != null) ValidtationResult(successful = true) else ValidtationResult(successful = false,errorMessage = "The medical folder can't be empty.")
        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatPasswordResult,
            phoneNumberResult,
            birthDateResult,
            firstNameResult,
            lastNameResult,
            addressResult,
            cityResult,
            countryResult,
            medicalFolderResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatPasswordResult.errorMessage,
                phone_numberError = phoneNumberResult.errorMessage,
                birth_dateError = birthDateResult.errorMessage,
                first_nameError = firstNameResult.errorMessage,
                last_nameError = lastNameResult.errorMessage,
                addressError = addressResult.errorMessage,
                cityError = cityResult.errorMessage,
                countryError = countryResult.errorMessage,
                medical_folderError = medicalFolderResult.errorMessage
            )
            return
        }


        val location = Location(
            state.address,
            state.city,
            state.country,
            state.latitude,
            state.longitude
        )
        val patient = Patient(
            null,
            state.birth_date,
            state.email_address,
            state.first_name,
            state.gender,
            null,
            state.last_name,
            location,
            "",
            null,
            state.password,
            state.phone_number
        )
        val email = state.email_address
        val password = state.password
        signUpUseCase(
            patient = patient,
            file = state.medical_folder!!
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    signInUseCase(
                        email = email,
                        password = password
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                validationEventChannel.send(ValidationEvent.Success)
                            }
                            is Resource.Error -> {
                                state =
                                    RegistrationFormState(error = result.message ?: "An unexpected error occured")
                            }
                            is Resource.Loading -> {
                                state = RegistrationFormState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    state =
                        RegistrationFormState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    state = RegistrationFormState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}