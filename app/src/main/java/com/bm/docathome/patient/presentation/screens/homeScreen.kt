package com.bm.docathome.patient.presentation.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.appointmentCard
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.navigation.ButtomBarItem
import com.bm.docathome.patient.presentation.navigation.Graph
import com.bm.docathome.patient.presentation.navigation.buttomNavBar
import com.bm.docathome.patient.presentation.ui.theme.*
import com.bm.docathome.patient.presentation.viewModel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun homeScreen(
    navController: NavController,
    addOneAppointment: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var isMenu by remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(56.dp),
                onClick = addOneAppointment,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(4.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(buttonsColor, CircleShape)
                        .size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "White",
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        },
        topBar = {
            state.patient?.let { patient ->
                if (isMenu) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(700.dp)
                            .background(
                                mainColor,
                                shape = RoundedCornerShape(
                                    bottomEndPercent = 10,
                                    bottomStartPercent = 10
                                )
                            )
                            .padding(12.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier
                                .padding(25.dp)
                                .align(
                                    Alignment.TopEnd
                                )
                                .size(32.dp)
                                .clickable {
                                    isMenu = !isMenu
                                }
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(25.dp)
                        ) {
                            Text(
                                text = "${patient.first_name} ${patient.last_name}",
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 10.dp),
                                color = textColor
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 15.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_group),
                                    contentDescription = null,
                                    tint = textColor1
                                )
                                Text(
                                    text = patient.phone_number,
                                    color = textColor1,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            }
                        }
                        Column(Modifier.align(Alignment.Center)) {
                            menuItem(
                                id = R.drawable.ic_group_80,
                                title = "Profile",
                                isLogout = false
                            ) {
                                navController.navigate(ButtomBarItem.Profile.route)
                            }
                            menuItem(
                                id = R.drawable.ic_oo,
                                title = "Medical Records",
                                isLogout = false
                            ) {
                                navController.navigate(ButtomBarItem.MedicalRecord.route)
                            }
                            menuItem(
                                id = R.drawable.ic_planning,
                                title = "Appointments",
                                isLogout = false
                            ) {
                                navController.navigate(ButtomBarItem.Appointments.route)
                            }
                        }
                        menuItem(
                            id = R.drawable.ic_group_88,
                            title = "Logout",
                            isLogout = true,
                            modifier = Modifier.align(
                                Alignment.BottomCenter
                            )
                        ) {
                            viewModel.onEvent(RegistrationFormEvent.Logout)
                            navController.navigate(Graph.Authentication) {
                                popUpTo(Graph.Home) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                } else
                    homeTopBar(patient.imageBitmap) { isMenu = !isMenu }
            }
            if (state.error.isNotBlank()) {
                if (state.error == "forbidden") {
                    LaunchedEffect(key1 = "forbidden") {
                        navController.navigate(Graph.Authentication) {
                            popUpTo(Graph.Home) {
                                inclusive = true
                            }
                        }
                    }
                }else
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp)
                    )
                }
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
        },
        bottomBar = {
            buttomNavBar(navController)
        }
    ) { paddingValues ->
        if (!isMenu) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 10.dp)
            ) {
                Text(
                    "Types of appointments available :",
                    style = MaterialTheme.typography.body2,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(20.dp, 10.dp, 0.dp, 0.dp),
                    fontSize = 16.sp
                )
                appointmentCard(
                    id = R.drawable.ic_icons__brain,
                    medicalStaffName = "Doctors",
                    typeOfAppoin = "Body Checkout",
                    discreptionAppoin = "A body checkout is like a health checkup that comes to you." +
                            " A doctor visits your home to assess your overall health." +
                            " They measure things like your blood pressure, heart rate, " +
                            "and check how well you move and feel. They also ask about your medical history" +
                            " and any concerns you have. It's a personalized way to make sure you're doing well," +
                            " without having to leave the comfort of your home."
                )
                Divider(
                    modifier = Modifier.padding(45.dp, 10.dp, 45.dp, 0.dp),
                    color = Color.LightGray.copy(0.4f),
                    thickness = 2.dp,
                )
                appointmentCard(
                    id = R.drawable.ic_icons__lab,
                    medicalStaffName = "Nurses",
                    typeOfAppoin = "Home Care",
                    discreptionAppoin = "Home care is when nurse bring medical services" +
                            " to your home. They provide treatments and help tailored to your specific needs." +
                            " For example, they can give you medicine, take care of wounds, help you do exercises," +
                            " or assist with daily activities like bathing and dressing. " +
                            "Home care is all about making your life easier by giving you" +
                            " the care you need right in the place where you feel most comfortable - your own home."
                )

            }
        }

    }

}


@Composable
fun homeTopBar(
    imageBitmap: Bitmap? = null,
    onClickMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(
                mainColor,
                shape = RoundedCornerShape(bottomEndPercent = 10, bottomStartPercent = 10)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .padding(
                    10.dp,
                    15.dp,
                    40.dp,
                    10.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClickMenu) {
                Icon(
                    modifier = Modifier.size(30.dp),

                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "profile Icon",
                    tint = iconColor
                )
            }
            Box(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                if (imageBitmap != null)
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                    )
                else
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = CircleShape)
                            .border(3.dp, Color.White, CircleShape)

                    )
                Box(
                    contentAlignment = Alignment.BottomEnd, modifier = Modifier
                        .size(50.dp)
                        .padding(horizontal = 3.dp, vertical = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Green, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                            .size(12.dp)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Good Evening,",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(end = 60.dp),
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "We hope you are doing fine today.",
            style = MaterialTheme.typography.body2,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(20.dp, 6.dp, 0.dp, 0.dp),
            fontSize = 16.sp
        )

    }
}

@Composable
fun menuItem(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    title: String,
    isLogout: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { onClick() },
        horizontalArrangement = if (!isLogout) Arrangement.SpaceBetween else Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = title,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
        if (!isLogout) {

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}





