package com.bm.docathome.patient.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.domain.use_case.get_access_token.GetAccessTokenUseCase
import com.bm.docathome.patient.domain.use_case.get_patient_appointments.GetAppointmentsUseCase
import com.bm.docathome.patient.presentation.state.AppointmentsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val tokenIdManager: TokenIdManager,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(AppointmentsListState())
    val state: State<AppointmentsListState> = _state

    init {
        getAppointments()
    }

    private fun getAppointments() {

        getAppointmentsUseCase(tokenIdManager.getId()!!).onEach{ result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AppointmentsListState(appointments = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    if (result.message != "Unknown error") {
                        getAccessTokenUseCase().onEach {
                            when (it) {
                                is Resource.Success -> {
                                    getAppointments()
                                }
                                is Resource.Error -> {
                                    if (it.message == "Forbidden" || it.message == "HTTP 401 Unauthorized") {
                                        tokenIdManager.clearTokens()
                                        _state.value = AppointmentsListState(error = "forbidden")
                                    } else _state.value = AppointmentsListState(
                                        error = it.message
                                            ?: "An unexpected error occured, please restart the app!"
                                    )
                                }
                                is Resource.Loading -> {
                                    _state.value = AppointmentsListState(isLoading = true)
                                }
                            }
                        }.launchIn(viewModelScope)

                    } else
                        _state.value =
                            AppointmentsListState(error = "An unexpected error occured, please restart the app!")
                }
                is Resource.Loading -> {
                    _state.value = AppointmentsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
