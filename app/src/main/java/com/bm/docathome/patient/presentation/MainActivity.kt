package com.bm.docathome.patient.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.presentation.navigation.Graph
import com.bm.docathome.patient.presentation.navigation.RootNavigetionGraph
import com.bm.docathome.patient.presentation.ui.theme.DocAtHomePatientTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.google.accompanist.permissions.rememberPermissionState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenIdManager: TokenIdManager

    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocAtHomePatientTheme {

                // lets handle runtime permission
                val permissionState = rememberPermissionState(
                    permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                val startDestination = if(!permissionState.hasPermission)  Graph.FirstScreen
                    else if (tokenIdManager.isRefreshToken()) Graph.Home
                else Graph.Authentication

                // A surface container using the 'background' color from the theme
                //               signUpScreen()
                RootNavigetionGraph(navController = rememberNavController(), startDestination =startDestination)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DocAtHomePatientTheme {
        Greeting("Android")
    }
}