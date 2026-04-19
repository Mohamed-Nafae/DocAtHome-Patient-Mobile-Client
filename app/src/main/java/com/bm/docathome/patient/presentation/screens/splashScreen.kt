package com.bm.docathome.patient.presentation.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bm.docathome.patient.presentation.ui.theme.textColor2

@Composable
fun startapp(
    alphaAnim:Float,
    modifier: Modifier,
    painter: Painter,
    description:String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painter,
            contentDescription = description,
            modifier = Modifier
                .size(200.dp)
                .alpha(alphaAnim)
        )
        Text(
            modifier = Modifier.alpha(alphaAnim),
            text = "DocAtHome",
            style = MaterialTheme.typography.h6,
            color = textColor2,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
    }
}




