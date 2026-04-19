package com.bm.docathome.patient.presentation.navigation



import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bm.docathome.patient.R
import com.bm.docathome.patient.presentation.screens.appointmentsScreen
import com.bm.docathome.patient.presentation.screens.homeScreen
import com.bm.docathome.patient.presentation.screens.profileScreen
import com.bm.docathome.patient.presentation.screens.reportsScreen
import com.bm.docathome.patient.presentation.ui.theme.iconColor


fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.Home,
        startDestination = ButtomBarItem.Home.route
    ) {
        composable(route = ButtomBarItem.Home.route) {
            homeScreen(navController)
        }
        composable(route = ButtomBarItem.Profile.route) {
            profileScreen(navController = navController)
        }
        composable(route = ButtomBarItem.Appointments.route) {
            appointmentsScreen(navController = navController)
        }
        composable(route = ButtomBarItem.MedicalRecord.route) {
            reportsScreen(navController= navController)
        }
    }
}

sealed class ButtomBarItem(
    val route:String,
    val selectedColor: Color,
    val unselectedColor: Color,
    @DrawableRes val iconId:Int
){
    object Home:ButtomBarItem(
        route = "Home",
        selectedColor = iconColor,
        unselectedColor = Color.LightGray,
        iconId = R.drawable.ic_icons__home
    )
    object MedicalRecord:ButtomBarItem(
        route="MedicalRecord",
        selectedColor = iconColor,
        unselectedColor = Color.LightGray,
        iconId = R.drawable.ic_icons__heart
    )
    object Profile:ButtomBarItem(
        route = "Profile",
        selectedColor = iconColor,
        unselectedColor = Color.LightGray,
        iconId = R.drawable.ic_icons__user
    )
    object Appointments:ButtomBarItem(
        route = "Appointment",
        selectedColor = iconColor,
        unselectedColor = Color.LightGray,
        iconId = R.drawable.ic_planning
    )
}
