package com.bm.docathome.patient.presentation.screens

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.*
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2
import com.bm.docathome.patient.presentation.viewModel.ProfileViewModel
import com.bm.docathome.patient.presentation.viewModel.RegistrationViewModel
import java.io.File
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun signUpScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onSingUp: () -> Unit,
    singIn: () -> Unit
) {
    val state = viewModel.state
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val scrollState = rememberScrollState()
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = textColor2,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                LaunchedEffect(key1 = "event") {
                    viewModel.validationEvents.collect { event ->
                        when (event) {
                            is RegistrationViewModel.ValidationEvent.Success -> {
                                onSingUp()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Image(
                    modifier = Modifier
                        .heightIn(min = 30.dp)
                        .widthIn(min = 50.dp, max = 100.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_main_app_icon),
                    contentDescription = "the main app icon"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 50.dp),
                    text = "Sign Up",
                    color = textColor,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    modifier = Modifier
                        .padding(vertical = 0.dp, horizontal = 5.dp)
                        .align(Alignment.Start),
                    text = stringResource(id = R.string.login_Screen2),
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif
                )

                // first name
                outlined_text_field_conp(
                    value = state.first_name,
                    placeholder = "enter your first name",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.first_nameError != null,
                    prefexIcon = Icons.Default.Person,
                    label = "First name"
                ) {
                    viewModel.onEvent(RegistrationFormEvent.FirstNameChanged(it))
                }
                if (state.first_nameError != null) {
                    Text(
                        text = state.first_nameError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // last name
                outlined_text_field_conp(
                    value = state.last_name,
                    placeholder = "enter your last name",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.last_nameError != null,
                    prefexIcon = Icons.Default.Person,
                    label = "Last name"
                ) {
                    viewModel.onEvent(RegistrationFormEvent.LastNameChanged(it))
                }
                if (state.last_nameError != null) {
                    Text(
                        text = state.last_nameError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                //email address
                outlined_text_field_conp(
                    value = state.email_address,
                    placeholder = "enter your email address",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.emailError != null,
                    prefexIcon = Icons.Default.Email,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    label = "Email"
                ) {
                    viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
                }
                if (state.emailError != null) {
                    Text(
                        text = state.emailError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // birth Date
                text_field_date(
                    birth_date = state.birth_date,
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.birth_dateError != null
                ) {
                    viewModel.onEvent(RegistrationFormEvent.BirthDateChanged(it))
                }
                if (state.birth_dateError != null) {
                    Text(
                        text = state.birth_dateError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                filePdfUpladeScreen(viewModel = viewModel)
                if (state.medical_folderError != null) {
                    Text(
                        text = state.medical_folderError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))
                //Gender
                dropdown_menu(
                    label = "Gender",
                    modifier = Modifier.fillMaxWidth(),
                    init_text = state.gender,
                    listItem = listOf("Male", "Female")
                ) {
                    viewModel.onEvent(RegistrationFormEvent.GenderChanged(it))
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // city
                outlined_text_field_conp(
                    value = state.city,
                    placeholder = "enter your City",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.cityError != null,
                    prefexIcon = Icons.Default.Place,
                    label = "City"
                ) {
                    viewModel.onEvent(RegistrationFormEvent.CityChanged(it))
                }
                if (state.cityError != null) {
                    Text(
                        text = state.cityError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // Country
                outlined_text_field_conp(
                    value = state.country,
                    placeholder = "enter your Country",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.countryError != null,
                    prefexIcon = Icons.Default.Place,
                    label = "Country"
                ) {
                    viewModel.onEvent(RegistrationFormEvent.CountryChanged(it))
                }
                if (state.countryError != null) {
                    Text(
                        text = state.countryError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // Address
                outlined_text_field_conp(
                    value = state.address,
                    placeholder = "enter your current Address",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.addressError != null,
                    prefexIcon = Icons.Default.Place,
                    label = "Address"
                ) {
                    viewModel.onEvent(RegistrationFormEvent.AddressChanged(it))
                }
                if (state.addressError != null) {
                    Text(
                        text = state.addressError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // Phone number
                outlined_text_field_conp(
                    value = state.phone_number,
                    placeholder = "enter your Phone number",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.phone_numberError != null,
                    prefexIcon = Icons.Default.Phone,
                    label = "Phone number",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    )
                ) {
                    viewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(it))
                }
                if (state.phone_numberError != null) {
                    Text(
                        text = state.phone_numberError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // Password
                outlined_text_field_conp(
                    value = state.password,
                    placeholder = "enter a new password",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.passwordError != null,
                    prefexIcon = Icons.Default.Lock,
                    label = "Password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    isPassword = true
                ) {
                    viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                }
                if (state.passwordError != null) {
                    Text(
                        text = state.passwordError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.5.dp))

                // Repeated Password
                outlined_text_field_conp(
                    value = state.repeatedPassword,
                    placeholder = "confirm your new password",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.repeatedPasswordError != null,
                    prefexIcon = Icons.Default.Lock,
                    label = "Confirm Password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    last = true,
                    isPassword = true
                ) {
                    viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                }
                if (state.repeatedPasswordError != null) {
                    Text(
                        text = state.repeatedPasswordError,
                        color = MaterialTheme.colors.error,
                        fontWeight = MaterialTheme.typography.overline.fontWeight,
                        fontSize = MaterialTheme.typography.overline.fontSize,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 10.dp)
                    )
                }

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                } else Spacer(modifier = Modifier.height(100.dp))

                button_comp(text = "Sign Up", modifier = Modifier.padding(horizontal = 0.dp)) {
                    viewModel.onEvent(RegistrationFormEvent.SignUp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        color = Color.Gray.copy(alpha = 0.4f),
                        thickness = 1.dp
                    )
                    Text(
                        modifier = Modifier.padding(
                            end = 8.dp,
                            start = 8.dp,
                            bottom = 5.dp,
                            top = 0.dp
                        ), text = "or", fontSize = 18.sp, color = textColor
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        color = Color.Gray.copy(alpha = 0.4f),
                        thickness = 1.dp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.Center,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account?",
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.W600,
                        color = textColor,
                        fontSize = 14.sp
                    )
                    clickableText(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        text = "Sign In",
                        color = textColor2,
                        onClick = singIn
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


// this modification
@Composable
fun filePdfUpladeScreen(
    viewModel: RegistrationViewModel
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val file =
                    File(context.cacheDir, "medicalFolder.pdf")
                file.createNewFile()
                context.contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                viewModel.onEvent(RegistrationFormEvent.MedicalFolderChanged(file))
            }
        }

    OutlinedTextField(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .onFocusEvent {
                if (it.isFocused)
                    launcher.launch("application/pdf")
            },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.FileUpload,
                contentDescription = "Show date dialog",
                tint = Color.LightGray
            )
        },
        value = if(viewModel.state.medical_folder != null) viewModel.state.medical_folder!!.name else "",
        onValueChange = {},
        shape = RoundedCornerShape(25),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedLabelColor = textColor2,
            cursorColor = textColor2,
            focusedIndicatorColor = textColor2,
            unfocusedLabelColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.LightGray
        ),
        placeholder = {
            Text(
                text = "Choose Your medical folder",
                fontWeight = FontWeight.Normal
            )
        },
        label = {
            Text(text = "Medical Folder")
        },
        isError = viewModel.state.medical_folderError != null,
        singleLine = true,
        readOnly = true
    )



}
