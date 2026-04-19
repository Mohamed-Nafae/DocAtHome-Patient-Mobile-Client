package com.bm.docathome.patient.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.button_comp
import com.bm.docathome.patient.presentation.component.profileItem_text_field
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.navigation.Graph
import com.bm.docathome.patient.presentation.navigation.buttomNavBar
import com.bm.docathome.patient.presentation.ui.theme.mainColor
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2
import com.bm.docathome.patient.presentation.viewModel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun profileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val editState = viewModel.editeState
    var isEditing by remember {
        mutableStateOf(false)
    }
    var isEdited by remember {
        mutableStateOf(true)
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = CircleShape,
                backgroundColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp,0.dp,0.dp)
            ) {}
        },
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(mainColor),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 5.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        ),
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "profile Icon",
                        tint = textColor
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "Profile",
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
        },
        bottomBar = {
            buttomNavBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(bottom = 3.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    color = mainColor,
                    shape = RoundedCornerShape(bottomEndPercent = 10, bottomStartPercent = 10)
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            state.patient?.let { patient ->
                Text(
                    modifier = Modifier
                        .padding(horizontal = 35.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    text = "Take a look at your profile",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                    if (patient.imageBitmap != null)
                        Image(
                            bitmap = patient.imageBitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(shape = CircleShape)
                                .border(5.dp, Color.White, CircleShape)
                        )
                    else Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(shape = CircleShape)
                            .border(5.dp, Color.White, CircleShape)
                    )
                    Box(
                        contentAlignment = Alignment.BottomEnd, modifier = Modifier
                            .size(200.dp)
                            .padding(horizontal = 13.dp, vertical = 7.dp)
                    ) {
                        imageUploadScreen(viewModel)
                    }

                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 35.dp, vertical = 5.dp),
                    text = "Personal Informations",
                    fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "First name",
                    isError = editState.first_nameError != null,
                    value = editState.first_name,
                    isEdited = isEdited,
                    isEditing = isEditing,
                    onclickIcons = { isEditing = true;isEdited = false }
                ) {
                    viewModel.onEvent(RegistrationFormEvent.FirstNameChanged(it))
                }
                if (editState.first_nameError != null) {
                    Text(
                        text = editState.first_nameError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 35.dp)
                    )
                }

                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Last name",
                    isError = editState.last_nameError != null,
                    value = editState.last_name,
                    isEdited = isEdited,
                    isEditing = isEditing,
                    onclickIcons = { isEditing = true;isEdited = false }
                ) {
                    viewModel.onEvent(RegistrationFormEvent.LastNameChanged(it))
                }
                if (editState.last_nameError != null) {
                    Text(
                        text = editState.last_nameError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 35.dp)
                    )
                }

                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Gender",
                    value = patient.gender
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Birth date",
                    value = "${patient.birth_date.dayOfWeek.name.lowercase()},\t ${patient.birth_date.dayOfMonth} ${patient.birth_date.month.name.lowercase()} ${patient.birth_date.year}"
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Email",
                    value = patient.email_address
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Phone number",
                    value = editState.phone_number,
                    isError = editState.phone_numberError != null,
                    isEdited = isEdited,
                    isEditing = isEditing,
                    onclickIcons = { isEditing = true;isEdited = false },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    last = true
                ) {
                    viewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(it))
                }
                if (editState.phone_numberError != null) {
                    Text(
                        text = editState.phone_numberError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 35.dp)
                    )
                }

                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Address",
                    value = patient.location.address
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "City",
                    value = patient.location.city
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Country",
                    value = patient.location.country
                )
                profileItem_text_field(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Medical folder",
                    value = "",
                    isDownloaded = true,
                    onclickIcons = {viewModel.downloadMedicalFolder()}
                )
                Spacer(modifier = Modifier.height(20.dp))
                if (isEditing) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp, vertical = 20.dp)
                    ) {
                        button_comp(
                            text = "Confirm",
                            borderRaduis = 20,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                        ) {
                            isEditing = false
                            isEdited = true
                            viewModel.onEvent(RegistrationFormEvent.Confirm)
                        }
                        button_comp(
                            text = "Cancel",
                            borderRaduis = 20,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                        ) {
                            isEditing = false
                            isEdited = true
                            viewModel.onEvent(RegistrationFormEvent.Cancel)
                        }
                    }
                }
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


@Composable
fun imageUploadScreen(
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val file =
                    File(context.cacheDir, "${viewModel.state.value.patient!!.first_name}Image.png")
                file.createNewFile()
                context.contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                viewModel.updateImage(file)
            }
        }

    IconButton(
        modifier = Modifier
            .background(Color.White, CircleShape)
            .size(40.dp),
        onClick = { launcher.launch("image/*") }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit_image),
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .padding(start = 4.dp, top = 3.dp),
            tint = Color.Gray

        )
    }
}
