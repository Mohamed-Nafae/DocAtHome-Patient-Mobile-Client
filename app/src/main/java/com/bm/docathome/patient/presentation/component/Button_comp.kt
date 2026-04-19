package com.bm.docathome.patient.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bm.docathome.patient.presentation.ui.theme.buttonsColor


@Composable
fun button_comp(
    modifier: Modifier = Modifier,
    borderRaduis: Int = 50,
    text: String,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(48.dp, max = 60.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 5.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        shape =RoundedCornerShape(borderRaduis),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(buttonsColor, shape = RoundedCornerShape(borderRaduis)),
            contentAlignment = Alignment.Center,

            ) {
            Text(
                text = text,
                color = Color.White,
                fontStyle = MaterialTheme.typography.button.fontStyle,
                fontFamily = MaterialTheme.typography.button.fontFamily,
                fontWeight = MaterialTheme.typography.button.fontWeight,
                fontSize = MaterialTheme.typography.button.fontSize
            )
        }
    }
}


@Preview(showBackground = true, heightDp = 500)
@Composable
fun test_BTn() {
    Surface(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Column() {
            button_comp(text = "wrong") {

            }
            Spacer(modifier = Modifier.heightIn(50.dp))
            button_comp(text = "hello") {

            }
        }

    }
}