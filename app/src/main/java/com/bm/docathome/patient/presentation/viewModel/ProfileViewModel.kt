package com.bm.docathome.patient.presentation.viewModel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.docathome.patient.R
import com.bm.docathome.patient.common.Constants
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.data.remote.dto.PatientUpdate
import com.bm.docathome.patient.domain.model.Patient
import com.bm.docathome.patient.domain.use_case.RegistrationForm.ValidateString
import com.bm.docathome.patient.domain.use_case.RegistrationForm.ValidationPhoneNumber
import com.bm.docathome.patient.domain.use_case.download_patient_image.DisplayImageUseCase
import com.bm.docathome.patient.domain.use_case.download_patient_medicalFolder.DownloadMedicalFolderUseCase
import com.bm.docathome.patient.domain.use_case.get_access_token.GetAccessTokenUseCase
import com.bm.docathome.patient.domain.use_case.get_patient.GetPatientUseCase
import com.bm.docathome.patient.domain.use_case.logout.LogoutUseCase
import com.bm.docathome.patient.domain.use_case.update_patient.UpdatePatientUseCase
import com.bm.docathome.patient.domain.use_case.update_patient_image.UpdateImageUseCase
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.state.EditingProfileState
import com.bm.docathome.patient.presentation.state.LoginState
import com.bm.docathome.patient.presentation.state.PatientState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase,
    private val tokenIdManager: TokenIdManager,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase,
    private val displayImageUseCase: DisplayImageUseCase,
    private val updateImageUseCase: UpdateImageUseCase,
    private val downloadMedicalFolderUseCase: DownloadMedicalFolderUseCase,
    private val logoutUseCase: LogoutUseCase,
    @ApplicationContext context: Context
) : ViewModel() {
    private val validationPhoneNumber: ValidationPhoneNumber = ValidationPhoneNumber()
    private val validateString: ValidateString = ValidateString()
    private val _state = mutableStateOf(PatientState())
    val state: State<PatientState> = _state
    private var patient = _state.value.patient
    var editeState by mutableStateOf(EditingProfileState())
    val context = context
    private val validationEventChannel = Channel<RegistrationViewModel.ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        getPatient()
    }


    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.PhoneNumberChanged -> {
                editeState = editeState.copy(phone_number = event.phone_number)
            }
            is RegistrationFormEvent.FirstNameChanged -> {
                editeState = editeState.copy(first_name = event.first_name)
            }
            is RegistrationFormEvent.LastNameChanged -> {
                editeState = editeState.copy(last_name = event.last_name)
            }
            is RegistrationFormEvent.Confirm -> {
                submitData()
            }
            is RegistrationFormEvent.Cancel -> {
                editeState = editeState.copy(
                    first_name = patient!!.first_name,
                    last_name = patient!!.last_name,
                    phone_number = patient!!.phone_number,
                    first_nameError = null,
                    last_nameError = null,
                    phone_numberError = null
                )
            }
            is RegistrationFormEvent.Logout -> {
                logout()
            }
        }
    }

    private fun logout(){
        logoutUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {}
                is Resource.Error -> {
                    _state.value = PatientState(
                        error = result.message
                            ?: "An unexpected error occured, please restart the app!"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PatientState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun submitData() {
        val phoneNumberResult = validationPhoneNumber.execute(editeState.phone_number)

        val firstNameResult =
            validateString.execute(string = editeState.first_name, stringName = "first name")
        val lastNameResult =
            validateString.execute(string = editeState.last_name, stringName = "last name")
        val hasError = listOf(
            phoneNumberResult,
            firstNameResult,
            lastNameResult
        ).any { !it.successful }

        if (hasError) {
            editeState = editeState.copy(
                phone_numberError = phoneNumberResult.errorMessage,
                first_nameError = firstNameResult.errorMessage,
                last_nameError = lastNameResult.errorMessage
            )
            return
        }

        editeState = editeState.copy(
            first_nameError = null,
            last_nameError = null,
            phone_numberError = null
        )

        val updatedPatient = patient?.let {
            if (it.phone_number == editeState.phone_number)
            PatientUpdate(
                first_name = editeState.first_name,
                last_name = editeState.last_name
            )
            else
                PatientUpdate(
                    editeState.first_name,
                    it.gender,
                    editeState.last_name,
                    editeState.phone_number
                )
        }

        //update patient
        updatePatientUseCase(
            tokenIdManager.getId()!!,
            updatedPatient!!
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getPatient()
                }
                is Resource.Error -> {
                    if (result.message != null) {
                        getAccessTokenUseCase().onEach { result ->
                            when (result) {
                                is Resource.Success -> {
                                    getPatient()
                                }
                                is Resource.Error -> {
                                    if (result.message == "Forbidden" || result.message == "HTTP 401 Unauthorized") {
                                        tokenIdManager.clearTokens()
                                        _state.value = PatientState(error = "forbidden")
                                    } else _state.value = PatientState(
                                        error = result.message
                                            ?: "An unexpected error occured, please restart the app!"
                                    )
                                }
                                is Resource.Loading -> {
                                    _state.value = PatientState(isLoading = true)
                                }
                            }
                        }.launchIn(viewModelScope)

                    } else
                        _state.value =
                            PatientState(error = "An unexpected error occured, please restart the app!")
                }
                is Resource.Loading -> {
                    _state.value = PatientState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            validationEventChannel.send(RegistrationViewModel.ValidationEvent.Success)
        }
    }

    private fun getPatient() {
        getPatientUseCase(tokenIdManager.getId()!!).onEach { result ->
            when (result) {
                is Resource.Success -> {

                    displayImageUseCase(tokenIdManager.getId()!!).onEach {
                        when (it) {
                            is Resource.Success -> {
                                val bitmap  = BitmapFactory.decodeStream(it.data?.body()?.byteStream())
                                _state.value = PatientState(patient = Patient(
                                    result.data?.appointments,
                                    result.data!!.birth_date,
                                    result.data.email_address,
                                    result.data.first_name,
                                    result.data.gender,
                                    bitmap,
                                    result.data.last_name,
                                    result.data.location,
                                    result.data.medical_folder,
                                    result.data.medical_reports,
                                    result.data.password,
                                    result.data.phone_number
                                ))
                                patient = _state.value.patient
                                editeState = editeState.copy(
                                    first_name = patient?.first_name ?: "",
                                    last_name = patient?.last_name ?: "",
                                    phone_number = patient?.phone_number ?: ""
                                )
                            }
                            is Resource.Error -> {
                                _state.value = _state.value.copy(patient=patient?.copy(imageBitmap = null))
                            }
                            is Resource.Loading -> {
                                _state.value = PatientState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    if (result.message != "Unknown error") {
                        getAccessTokenUseCase().onEach {
                            when (it) {
                                is Resource.Success -> {
                                    getPatient()
                                }
                                is Resource.Error -> {
                                    if (it.message == "Forbidden" || it.message == "HTTP 401 Unauthorized") {
                                        tokenIdManager.clearTokens()
                                        _state.value = PatientState(error = "forbidden")
                                    } else _state.value = PatientState(
                                        error = it.message
                                            ?: "An unexpected error occured, please restart the app!"
                                    )
                                }
                                is Resource.Loading -> {
                                    _state.value = PatientState(isLoading = true)
                                }
                            }
                        }.launchIn(viewModelScope)

                    } else
                        _state.value =
                            PatientState(error = "An unexpected error occured, please restart the app!")
                }
                is Resource.Loading -> {
                    _state.value = PatientState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateImage(file:File){
      updateImageUseCase(tokenIdManager.getId()!!,file).onEach { result ->
          when(result) {
              is Resource.Success -> getPatient()
              is Resource.Error -> getPatient()
              is Resource.Loading -> _state.value = PatientState(isLoading = true)

          }
      }.launchIn(viewModelScope)
    }

    fun downloadMedicalFolder() {
        downloadMedicalFolderUseCase(tokenIdManager.getId()!!).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val fileName = "medical_folder.pdf"
                    val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val outputFile = File(downloadsDirectory, fileName)

                    withContext(Dispatchers.IO) {
                        FileOutputStream(outputFile).use { output ->
                            resource.data?.body()?.byteStream().use { input ->
                                input?.copyTo(output)
                            }
                        }
                    }

                    // Show notification
                    val notificationManager = NotificationManagerCompat.from(context)
                    val channelId = "download_channel"
                    val channelName = "Download"
                    val notificationId = 123

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            channelId,
                            channelName,
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        notificationManager.createNotificationChannel(channel)
                    }

                    val notificationBuilder = NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_download_done_icon)
                        .setContentTitle("Download")
                        .setContentText("Medical folder downloaded successfully")
                        .setAutoCancel(true)

                    notificationManager.notify(notificationId, notificationBuilder.build())

                    getPatient()
                }
                is Resource.Error -> getPatient()
            }
        }.launchIn(viewModelScope)
    }



    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}
