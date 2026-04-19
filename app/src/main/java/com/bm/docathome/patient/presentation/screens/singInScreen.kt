package com.bm.docathome.patient.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.button_comp
import com.bm.docathome.patient.presentation.component.clickableText
import com.bm.docathome.patient.presentation.component.outlined_text_field_conp
import com.bm.docathome.patient.presentation.events.RegistrationFormEvent
import com.bm.docathome.patient.presentation.navigation.Graph
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2
import com.bm.docathome.patient.presentation.viewModel.SignInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun signInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onSingIn:() -> Unit,
    singUp:() -> Unit
) {
    val state = viewModel.state
    LaunchedEffect(
        key1 = state.success
    ) {
        if (state.success)
        onSingIn()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        if(state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = textColor2,modifier = Modifier.align(Alignment.Center).size(50.dp))
            }
        }
        else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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
                    text = stringResource(id = R.string.login_Screen1),
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

                //email address
                outlined_text_field_conp(
                    value = state.email_address,
                    placeholder = "enter your email address",
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.emailError != null,
                    prefexIcon = Icons.Default.Email,
                    label = "Email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
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
                    isPassword = true,
                    last = true
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
                if (state.error.isNotBlank()){
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( 20.dp)
                    )
                }else Spacer(modifier = Modifier.height(200.dp))

                button_comp(text = "SIGN IN") {
                    viewModel.onEvent(RegistrationFormEvent.SignIn)
                }

                Spacer(modifier = Modifier.height(5.dp))

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
                        text = stringResource(id = R.string.login_Screen3),
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.W600,
                        color = textColor,
                        fontSize = 14.sp
                    )
                    clickableText(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        text = stringResource(R.string.login_Screen4),
                        color = textColor2,
                        onClick = singUp
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun test2() {

}

