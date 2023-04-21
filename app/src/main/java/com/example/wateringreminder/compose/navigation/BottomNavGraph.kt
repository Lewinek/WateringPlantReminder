package com.example.wateringreminder.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wateringreminder.articlelist.MyPlantsScreen
import com.example.wateringreminder.watering.WateringScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Watering.route
    ) {
        composable(route = BottomBarScreen.Watering.route) {
            WateringScreen(
                onNavigateToPlantCreator = { navController.navigate("plantCreator")}
            )
        }
        composable(route = BottomBarScreen.MyPlants.route) {
            MyPlantsScreen()
        }
        plantNavGraph(navController)
    }
}