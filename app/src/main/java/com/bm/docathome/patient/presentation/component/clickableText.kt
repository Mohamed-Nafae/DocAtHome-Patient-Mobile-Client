package com.bm.docathome.patient.presentation.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.bm.docathome.patient.presentation.ui.theme.textColor2

@Composable
fun clickableText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Blue,
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = color,
                fontWeight = FontWeight.W600,
                fontSize = 13.5.sp,
            )

        ) {
            append(text)
            addStringAnnotation("clickable", "clickable", 0, text.length)
        }
    }
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations("clickable", offset, offset)
                .firstOrNull()?.let {
                    onClick()
                }
        },
        style = TextStyle.Default.copy(
            textAlign = TextAlign.Justify,
        )
    )
}