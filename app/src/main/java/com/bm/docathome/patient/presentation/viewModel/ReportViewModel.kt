package com.bm.docathome.patient.presentation.viewModel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bm.docathome.patient.R
import com.bm.docathome.patient.common.Resource
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.domain.use_case.download_patient_report.DownloadReportUseCase
import com.bm.docathome.patient.domain.use_case.get_access_token.GetAccessTokenUseCase
import com.bm.docathome.patient.domain.use_case.get_patient.GetPatientUseCase
import com.bm.docathome.patient.domain.use_case.get_patient_reports.GetReportsUseCase
import com.bm.docathome.patient.presentation.state.ReportsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val tokenIdManager: TokenIdManager,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getReportsUseCase: GetReportsUseCase,
    private val downloadReportUseCase: DownloadReportUseCase,
    @ApplicationContext context: Context
) : ViewModel() {
    private val _state = mutableStateOf(ReportsListState())
    val state: State<ReportsListState> = _state
    val context =context

    init {
        getReports()
    }

    private fun getReports() {

        getReportsUseCase(tokenIdManager.getId()!!).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ReportsListState(reports = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    if (result.message != "Unknown error") {
                        getAccessTokenUseCase().onEach {
                            when (it) {
                                is Resource.Success -> {
                                    getReports()
                                }
                                is Resource.Error -> {
                                    if (it.message == "Forbidden" || it.message == "HTTP 401 Unauthorized") {
                                        tokenIdManager.clearTokens()
                                        _state.value = ReportsListState(error = "forbidden")
                                    } else _state.value = ReportsListState(
                                        error = it.message
                                            ?: "An unexpected error occured, please restart the app!"
                                    )
                                }
                                is Resource.Loading -> {
                                    _state.value = ReportsListState(isLoading = true)
                                }
                            }
                        }.launchIn(viewModelScope)

                    } else
                        _state.value =
                            ReportsListState(error = "An unexpected error occured, please restart the app!")
                }
                is Resource.Loading -> {
                    _state.value = ReportsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun downloadMedicalFolder(reportId:String,reportName:String) {
        downloadReportUseCase(tokenIdManager.getId()!!,reportId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val fileName = "$reportName.pdf"
                    val downloadsDirectory = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS)
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
                    val notificationId = 124

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
                        .setContentText("Medical Record number ${reportName.get(reportName.length-1)} downloaded successfully")
                        .setAutoCancel(true)

                    notificationManager.notify(notificationId, notificationBuilder.build())

                    getReports()
                }
                is Resource.Error -> getReports()
            }
        }.launchIn(viewModelScope)
    }
}
