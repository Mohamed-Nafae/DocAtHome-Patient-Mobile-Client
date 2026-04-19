package com.bm.docathome.patient.presentation.component

import android.os.Build
import android.service.autofill.DateTransformation
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.ui.window.DialogProperties
import com.bm.docathome.patient.domain.use_case.RegistrationForm.ValidateBirthDate
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bm.docathome.patient.domain.use_case.RegistrationForm.ValidateRepeatPassword
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun text_field_date(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    birth_date: LocalDate,
    onValueChange: (LocalDate) -> Unit
) {
    var birthDate by remember {
        mutableStateOf(birth_date)
    }
    val dateFormat by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/YYYY")
                .format(birthDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .padding(3.dp)
            .onFocusEvent {
                if (it.isFocused)
                dateDialogState.show()
            },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = "Show date dialog",
                tint = Color.LightGray
            )
        },
        value = dateFormat,
        shape = RoundedCornerShape(25),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedLabelColor = textColor2,
            cursorColor = textColor2,
            focusedIndicatorColor = textColor2,
            unfocusedLabelColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.LightGray
        ),
        label = {
            Text(
                text = "Birth Date"
            )
        },
        isError = isError,
        singleLine = true,
        onValueChange = {},
        readOnly = true
    )


    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                text = "Confirm",
                textStyle = MaterialTheme.typography.button.copy(color = textColor2)
            ) {
                dateDialogState.hide(focusManager = focusManager)
            }
            negativeButton(
                text = "Cancel",
                textStyle = MaterialTheme.typography.button.copy(color = textColor2)
            ) {
                dateDialogState.hide(focusManager = focusManager)
            }
        },
        shape = RoundedCornerShape(2),
    ) {
        datepicker(
            initialDate = birthDate,
            title = "Choose your birth date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = textColor2,
                headerTextColor = Color.White,
                dateActiveBackgroundColor = textColor2,
                dateActiveTextColor = Color.White
            ),
            allowedDateValidator = {
                it.isBefore(LocalDate.now())
            }
        ) {
            onValueChange(it)
            birthDate = it
        }
    }
}


