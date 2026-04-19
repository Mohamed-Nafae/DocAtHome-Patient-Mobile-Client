package com.bm.docathome.patient.presentation.screens


import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bm.docathome.patient.presentation.navigation.buttomNavBar
import com.bm.docathome.patient.presentation.ui.theme.mainColor
import com.bm.docathome.patient.presentation.ui.theme.textColor
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.button_comp
import com.bm.docathome.patient.presentation.component.profileItem_text_field
import com.bm.docathome.patient.presentation.component.reportItem
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.navigation.Graph
import com.bm.docathome.patient.presentation.ui.theme.textColor2
import com.bm.docathome.patient.presentation.viewModel.AppointmentViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

@Composable
fun appointmentsScreen(
    viewModel: AppointmentViewModel = hiltViewModel(),
    navController: NavController
) {
    val isInfo = rememberSaveable {
        mutableStateOf(false)
    }
    val index = rememberSaveable() {
        mutableStateOf(0)
    }
    val state = viewModel.state.value
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = CircleShape,
                backgroundColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            ) {}
        },
        topBar = {
            if (isInfo.value) listTopBar("Appointment Info"){isInfo.value = !isInfo.value}
            else listTopBar()
        },
        bottomBar = {
            buttomNavBar(navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = 3.dp)
                .fillMaxSize()
                .background(
                    color = mainColor,
                    shape = RoundedCornerShape(bottomEndPercent = 8, bottomStartPercent = 8)
                )
        ) {
            if (state.appointments.isEmpty() && !state.isLoading && state.error.isNullOrEmpty()) {
                Text(
                    text = "You don't have any Appointments!",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = MaterialTheme.typography.h1.fontWeight,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontFamily = MaterialTheme.typography.overline.fontFamily,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                if (!isInfo.value) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {

                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        items(state.appointments) { appointment ->
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 25.dp)
                                    .border(
                                        1.dp,
                                        Color.LightGray.copy(0.6f),
                                        shape = RoundedCornerShape(10)
                                    )
                                    .shadow(1.dp, RoundedCornerShape(10))
                                    .fillMaxWidth()
                                    .background(color = Color.White, shape = RoundedCornerShape(10))
                                    .height(100.dp)
                                    .clickable {
                                        isInfo.value = !isInfo.value
                                        index.value = state.appointments.indexOf(appointment)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(20.dp, 10.dp, 0.dp, 10.dp)
                                        .size(60.dp)
                                        .background(color = textColor2, RoundedCornerShape(10))

                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Schedule,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                                Row(
                                    Modifier
                                        .padding(horizontal = 10.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = appointment.typeofAppointment,
                                        color = textColor,
                                        fontWeight = FontWeight.W500,
                                        fontSize = 18.sp
                                    )
                                }

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding()
                            .padding(bottom = 3.dp)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                    ) {
                    val appointment = state.appointments[index.value]
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 35.dp, vertical = 5.dp)
                            .fillMaxWidth(),
                        text = "Take a look at your Appointment",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(220.dp)
                            .clip(RoundedCornerShape(10)),
                        Alignment.Center
                    ) {
                        QRCodeView(content = appointment.qrCode_id, size = 200.dp)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 35.dp, vertical = 5.dp),
                        text = "Appiontment Informations",
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        color = textColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )

                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "TypeofAppointment",
                        value = appointment.typeofAppointment
                    )
                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Appointment Date",
                        value = if (appointment.date != null) "${appointment.date.dayOfWeek.name.lowercase()},\t ${appointment.date.dayOfMonth} ${appointment.date.month.name.lowercase()} ${appointment.date.year}" else "this appointment doesn't have a date, wait until the admin give to you a date."
                    )
                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Status",
                        value = appointment.status!!
                    )

                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Address",
                        value = appointment.location.address
                    )
                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "City",
                        value = appointment.location.city
                    )
                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Country",
                        value = appointment.location.country
                    )
                    profileItem_text_field(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Care Taker",
                        value = if (appointment.careTaker) "is for another Patient" else "is for you"
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }}
            if (state.error.isNotBlank()) {
                if (state.error == "forbidden") {
                    LaunchedEffect(key1 = "forbidden") {
                        navController.navigate(Graph.Authentication) {
                            popUpTo(Graph.Home) {
                                inclusive = true
                            }
                        }
                    }
                } else
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = textColor2, modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                    )
                }
            }
        }

    }
}

//QRCodeView(
//content = "Your QR code content",
//modifier = Modifier.fillMaxSize(),
//size = 300.dp
//)
@Composable
fun QRCodeView(content: String, modifier: Modifier = Modifier, size: Dp) {
    val qrCodeSize = with(LocalDensity.current) { size.toPx() }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.size(size)) {
            drawRect(Color.White, size = Size(qrCodeSize, qrCodeSize))

            val qrCodeBitmap = generateQRCodeBitmap(content, qrCodeSize.toInt())
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawBitmap(qrCodeBitmap, 0f, 0f, null)
            }
        }
    }
}

private fun generateQRCodeBitmap(content: String, size: Int): Bitmap {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

    try {
        val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (bitMatrix.get(
                        x,
                        y
                    )
                ) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    } catch (e: WriterException) {
        throw RuntimeException("Error generating QR code", e)
    }
}

@Composable
fun listTopBar(
    text: String = "My Appointments",
    onClickIcon: () -> Unit ={}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(mainColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (text == "My Appointments") {
                Icon(
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 0.dp
                        )
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.ic_planning),
                    contentDescription = "Appointment Icon",
                    tint = textColor
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 0.dp
                        )
                        .size(30.dp)
                        .clickable { onClickIcon() },
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Appointment Icon",
                    tint = textColor
                )
            }
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = text,
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = textColor,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.shadow(1.5.dp)
        )
    }
}