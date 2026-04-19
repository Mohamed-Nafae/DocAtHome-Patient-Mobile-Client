package com.bm.docathome.patient.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2

@Composable
fun reportItem(
    modifier: Modifier = Modifier,
    day: String,
    month: String,
    index: Int,
    onClickIcon: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 5.dp, horizontal = 25.dp)
            .border(1.dp, Color.LightGray.copy(0.6f), shape = RoundedCornerShape(10))
            .shadow(1.dp, RoundedCornerShape(10))
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10))
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp, 10.dp, 0.dp, 10.dp)
                .size(60.dp)
                .background(color = textColor2, RoundedCornerShape(10))

        ) {
            Text(text = day, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = month, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Row(
            Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Record $index",
                color = textColor,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
            )
            IconButton(onClick = onClickIcon) {
                Icon(
                    imageVector = Icons.Outlined.Download,
                    contentDescription = "Download",
                    tint = textColor
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun report_Item() {
    Column(Modifier.fillMaxSize()) {
        val value = remember {
            mutableStateOf("")
        }
        reportItem(day = "24", month = "FEB", index = 1) {

        }
        reportItem(day = "24", month = "FEB", index = 1) {

        }
    }

}