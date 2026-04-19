package com.bm.docathome.patient.presentation.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.button_comp
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun firstScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    SideEffect {
        if (!permissionState.hasPermission) {
            permissionState.launchPermissionRequest()
        }

    }

    Surface(Modifier.fillMaxSize(), color = Color.White){
        Column(
            modifier = modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .heightIn(min = 30.dp)
                    .widthIn(min = 50.dp, max = 100.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_main_app_icon),
                contentDescription = "the main app icon"
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.padding(horizontal = 50.dp),
                text = stringResource(id = R.string.first_screen1),
                color = textColor,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                modifier = Modifier.padding(horizontal = 50.dp, vertical = 5.dp),
                text = stringResource(id = R.string.first_screen2),
                color = textColor2,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                    .heightIn(300.dp),
                painter = painterResource(id = R.drawable.doctor),
                contentDescription = "doctor"
            )
            Spacer(modifier = Modifier.height(80.dp))
            button_comp(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.first_screen3),
                onClick = onClick
            )

            Spacer(modifier = Modifier.height(50.dp))


        }
    }
}


@Preview(showBackground = true)
@Composable
fun test() {
    firstScreen() {

    }
}