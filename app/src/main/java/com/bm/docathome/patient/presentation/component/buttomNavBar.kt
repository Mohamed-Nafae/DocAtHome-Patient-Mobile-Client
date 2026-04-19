package com.bm.docathome.patient.presentation.navigation

import android.widget.Space
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun navItem(item: ButtomBarItem,isSelected:Boolean,onClick:()->Unit){
    val iconColor = if (isSelected) item.selectedColor else item.unselectedColor
    IconButton(onClick = onClick) {
        Icon(painter = painterResource(id = item.iconId), contentDescription = item.route, tint = iconColor )
    }
}

@Composable
fun buttomNavBar (navController: NavController){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomAppBar (
        backgroundColor = Color.White,
        cutoutShape = CircleShape,
        contentPadding = PaddingValues(horizontal = 25.dp),
        elevation = 4.dp
    ){

            ButtomBarItem.Home.let {
                navItem(item = it, isSelected = it.route == backStackEntry.value?.destination?.route) {
                    navController.navigate(it.route)
                }
            }
            Spacer(Modifier.weight(1f))
            ButtomBarItem.Appointments.let {
                navItem(item = it, isSelected = it.route == backStackEntry.value?.destination?.route) {
                    navController.navigate(it.route)
                }
            }
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.weight(1f))
            ButtomBarItem.MedicalRecord.let {
                navItem(item = it, isSelected = it.route == backStackEntry.value?.destination?.route) {
                    navController.navigate(it.route)
                }
            }
            Spacer(Modifier.weight(1f))
            ButtomBarItem.Profile.let {
                navItem(
                    item = it,
                    isSelected = it.route == backStackEntry.value?.destination?.route
                ) {
                    navController.navigate(it.route)
                }
            }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewNavItem(){
    navItem(item = ButtomBarItem.Home, isSelected = false) {

    }
}