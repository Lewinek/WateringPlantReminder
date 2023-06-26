package com.example.wateringreminder.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wateringreminder.myplants.MyPlantsScreen
import com.example.wateringreminder.watering.WateringScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Watering.route
    ) {
        composable(route = BottomBarScreen.Watering.route) {
            WateringScreen()
        }
        composable(route = BottomBarScreen.MyPlants.route) {
            MyPlantsScreen(onNavigateToPlantCreator = { navController.navigate("plantCreator") })
        }
        plantNavGraph(navController)
    }
}