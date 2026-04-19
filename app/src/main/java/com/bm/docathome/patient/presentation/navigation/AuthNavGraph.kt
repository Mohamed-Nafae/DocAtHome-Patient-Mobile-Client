package com.bm.docathome.patient.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bm.docathome.patient.presentation.screens.signInScreen
import com.bm.docathome.patient.presentation.screens.signUpScreen

@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.Authentication,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            signInScreen(
                onSingIn = {
                    navController.navigate(Graph.Home){
                        popUpTo(Graph.Authentication){
                            inclusive = true
                        }
                    }
                },
                singUp = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            signUpScreen(
                onSingUp = {
                    navController.navigate(Graph.Home){
                        popUpTo(Graph.Authentication){
                            inclusive = true
                        }
                    }
                },
                singIn = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }

    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
}