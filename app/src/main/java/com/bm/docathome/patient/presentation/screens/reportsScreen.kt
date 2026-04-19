package com.bm.docathome.patient.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.component.reportItem
import com.bm.docathome.patient.presentation.navigation.Graph
import com.bm.docathome.patient.presentation.navigation.buttomNavBar
import com.bm.docathome.patient.presentation.ui.theme.mainColor
import com.bm.docathome.patient.presentation.ui.theme.textColor
import com.bm.docathome.patient.presentation.ui.theme.textColor2
import com.bm.docathome.patient.presentation.viewModel.ReportViewModel

@Composable
fun reportsScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = CircleShape,
                backgroundColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            ) {}
        },
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(mainColor),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(
                                start = 15.dp,
                                end = 10.dp,
                                top = 5.dp,
                                bottom = 0.dp
                            )
                            .size(35.dp),
                        painter = painterResource(id = R.drawable.ic_oo),
                        contentDescription = "report Icon",
                        tint = textColor
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = "My medical records",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        color = textColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.shadow(1.5.dp)
                )
            }
        },
        bottomBar = {
            buttomNavBar(navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = 3.dp)
                .fillMaxSize()
                .background(
                    color = mainColor,
                    shape = RoundedCornerShape(bottomEndPercent = 8, bottomStartPercent = 8)
                )
        ) {
            if (state.reports.isEmpty() && !state.isLoading && state.error.isNullOrEmpty()){
                Text(
                    text = "You don't have any medical Records!",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = MaterialTheme.typography.h1.fontWeight,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontFamily = MaterialTheme.typography.overline.fontFamily,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                    items(state.reports) { report ->
                    reportItem(
                        day = if (report.createdAt.dayOfMonth.toString().length == 1) "0${report.createdAt.dayOfMonth}" else report.createdAt.dayOfMonth.toString(),
                        month = report.createdAt.month.name.substring(0, 3),
                        index = state.reports.indexOf(report) + 1
                    ) {
                        viewModel.downloadMedicalFolder(report._id,"medical_record${state.reports.indexOf(report) + 1}")
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                }
                          }
            if (state.error.isNotBlank()) {
                if (state.error == "forbidden") {
                    LaunchedEffect(key1 = "forbidden") {
                        navController.navigate(Graph.Authentication) {
                            popUpTo(Graph.Home) {
                                inclusive = true
                            }
                        }
                    }
                }else
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = textColor2, modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                    )
                }
           }
        }
    }
}