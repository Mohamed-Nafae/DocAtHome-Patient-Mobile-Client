package com.bm.docathome.patient.presentation.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import com.bm.docathome.patient.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bm.docathome.patient.presentation.screens.firstScreen
import com.bm.docathome.patient.presentation.screens.startapp
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun RootNavigetionGraph(
    navController: NavHostController,
    startDestination: String
){
    NavHost(navController = navController, route = Graph.ROOT, startDestination = Graph.SplashScreen){
        homeNavGraph(navController)
        authNavGraph(navController)
        composable(route = Graph.SplashScreen){
            var startAnimation by remember {
                mutableStateOf(false)
            }
            val alphaAnim = animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 3000
                )
            )
            LaunchedEffect(key1 = true) {
                startAnimation = true
                navController.popBackStack()
                delay(2000)
                navController.navigate(startDestination)
            }
            startapp(
                alphaAnim = alphaAnim.value,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White
                    ),
                painter = painterResource(id = R.drawable.main_app_ac_foreground ),
                description = "start app icon"
            )
        }
        composable(Graph.FirstScreen){
            firstScreen {
                navController.popBackStack()
                navController.navigate(Graph.Authentication)
            }
        }
    }
}


object Graph{
    const val ROOT = "root_graph"
    const val Authentication = "auth_graph"
    const val Home = "home_graph"
    const val FirstScreen = "first_screen"
    const val SplashScreen = "splash_screen"
}