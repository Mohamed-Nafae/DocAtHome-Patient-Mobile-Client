package com.bm.docathome.patient.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.bm.docathome.patient.presentation.ui.theme.mainColor
import com.bm.docathome.patient.presentation.ui.theme.textColor1
import com.bm.docathome.patient.presentation.ui.theme.textColor2

@Composable
fun profileItem_text_field(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    placeholder: String = "",
    isEditing: Boolean = false,
    isDownloaded: Boolean = false,
    isEdited: Boolean = false,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    visualTransformation: VisualTransformation? = null,
    last: Boolean = false,
    onclickIcons: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .padding(vertical = 5.dp, horizontal = 35.dp)
            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(20))
            .shadow(3.dp, shape = RoundedCornerShape(20), spotColor = Color.LightGray),
        placeholder = {
            Text(
                text = placeholder,
                fontWeight = FontWeight.Normal
            )
        },
        enabled = isEditing,
        readOnly = !isEditing,
        trailingIcon = {
            if (isEdited || isDownloaded) {
                IconButton(
                    onClick = onclickIcons
                ) {
                    if (isEdited)
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit",
                            tint = Color.LightGray
                        )
                    else
                        Icon(
                        imageVector = Icons.Outlined.Download,
                        contentDescription = "Download",
                        tint = Color.LightGray
                    )
                }
            }
        },
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(20),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            placeholderColor = Color.LightGray,
            focusedLabelColor = textColor2,
            cursorColor = textColor2,
            focusedIndicatorColor = textColor2,
            unfocusedLabelColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        label = {
            Text(
                text = label,
                color = textColor1,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.body2.fontSize
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
        else visualTransformation
    )
}


@Preview(showBackground = true)
@Composable
fun profileItem_textfield() {
    Column() {
        val value = remember {
            mutableStateOf("")
        }
        profileItem_text_field(
            label = "password",
            value = "password"
        )
        Spacer(modifier = Modifier.height(200.dp))
        outlined_text_field_conp(placeholder = "password", onValueChange = {
            value.value = it
        })
    }

}
