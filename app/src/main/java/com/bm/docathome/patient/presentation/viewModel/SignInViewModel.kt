package com.bm.docathome.patient.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.data.remote.dto.AuthResponse
import com.bm.docathome.patient.domain.use_case.RegistrationForm.*
import com.bm.docathome.patient.domain.use_case.sign_in.SignInUseCase
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.state.LoginState
import com.bm.docathome.patient.presentation.state.PatientState
import com.bm.docathome.patient.presentation.state.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    var state by mutableStateOf(LoginState())

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email_address = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is RegistrationFormEvent.SignIn -> {
                submitData()
            }

        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email_address)
        val passwordResult = validatePassword.execute(state.password)
        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        signInUseCase(
            email = state.email_address,
            password = state.password
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = LoginState(success = true)
                }
                is Resource.Error -> {
                    state =
                        LoginState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    state = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}
