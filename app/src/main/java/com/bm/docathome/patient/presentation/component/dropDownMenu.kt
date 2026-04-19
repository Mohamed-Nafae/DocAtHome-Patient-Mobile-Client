package com.bm.docathome.patient.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.rounded.Transgender
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bm.docathome.patient.presentation.ui.theme.textColor2


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dropdown_menu(
    label: String? = null,
    modifier: Modifier = Modifier,
    init_text: String,
    listItem: List<String>,
    onChange: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var init_item by remember {
        mutableStateOf(init_text)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            modifier = modifier
                .padding(3.dp),
            value = init_item,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                if (init_item == "Male")
                Icon(
                    imageVector = Icons.Default.Male,
                    contentDescription = "sufixIcon",
                    tint = Color.LightGray
                )
                else Icon(
                    imageVector = Icons.Default.Female,
                    contentDescription = "sufixIcon",
                    tint = Color.LightGray
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = { if (label != null) Text(text = label) },
            shape = RoundedCornerShape(25),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = Color.LightGray,
                focusedLabelColor = textColor2,
                cursorColor = textColor2,
                focusedIndicatorColor = textColor2,
                unfocusedLabelColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            listItem.forEach {
                DropdownMenuItem(onClick = {
                    isExpanded = false
                    init_item = it
                    onChange(it)
                }) {
                    Text(text = it)
                }
            }
        }
    }
}


