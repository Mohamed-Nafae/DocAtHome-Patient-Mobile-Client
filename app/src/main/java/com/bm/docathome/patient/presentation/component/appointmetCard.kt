package com.bm.docathome.patient.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bm.docathome.patient.presentation.ui.theme.textColor


@ExperimentalMaterialApi
@Composable
fun appointmentCard(
    medicalStaffName:String,
    typeOfAppoin: String,
    discreptionAppoin:String,
    modifier: Modifier = Modifier,
    @DrawableRes id: Int
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp),
        shape = RoundedCornerShape(10),
        elevation = 0.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = "first icon",
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp),
                tint = textColor
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
            ) {
                Text(
                    medicalStaffName,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray.copy(0.8f),
                    fontSize = MaterialTheme.typography.body2.fontSize
                )
                Text(typeOfAppoin,
                    modifier = Modifier.padding(vertical = 5.dp),
                    style = MaterialTheme.typography.subtitle1,
                    color = textColor,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    discreptionAppoin,
                    modifier = Modifier.padding(top =3.dp),
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray.copy(0.8f),
                    fontSize = MaterialTheme.typography.body2.fontSize
                )
            }
        }
    }
}