package com.bm.docathome.patient.presentation.component


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
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bm.docathome.patient.presentation.ui.theme.textColor2


@Composable
fun outlined_text_field_conp(
    modifier: Modifier = Modifier,
    value: String = "",
    isError: Boolean = false,
    placeholder: String,
    prefexIcon: ImageVector? = null,
    isPassword: Boolean = false,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    visualTransformation: VisualTransformation? = null,
    last: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var showPassword by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .padding(3.dp),
        placeholder = {
            Text(
                text = placeholder,
                fontWeight = FontWeight.Normal
            )
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    showPassword = !showPassword
                }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (showPassword) "Show Password" else "Hide Password",
                        tint = Color.LightGray
                    )
                }
            }
        },
        leadingIcon = {
            if (prefexIcon != null)
                Icon(
                    imageVector = prefexIcon,
                    contentDescription = "sufixIcon",
                    tint = Color.LightGray
                )
        },
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(25),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            placeholderColor = Color.LightGray,
            focusedLabelColor = textColor2,
            cursorColor = textColor2,
            focusedIndicatorColor = textColor2,
            unfocusedLabelColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.LightGray
        ),
        label = {
            Text(
                text = label
            )
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                if (!last) focusManager.moveFocus(FocusDirection.Next)
                else focusManager.clearFocus()

            }
        ),
        singleLine = true,
        visualTransformation = if (visualTransformation == null) VisualTransformation.None
        else if(showPassword) VisualTransformation.None
        else visualTransformation
    )
}


@Preview(showBackground = true)
@Composable
fun textfield() {
    Column() {
        val value = remember {
            mutableStateOf("")
        }
        outlined_text_field_conp(
            prefexIcon = Icons.Default.Lock,
            placeholder = "password",
            onValueChange = {})
        Spacer(modifier = Modifier.height(200.dp))
        outlined_text_field_conp(placeholder = "password", onValueChange = {
            value.value = it
        })
    }

}