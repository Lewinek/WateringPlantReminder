package com.example.wateringreminder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.wateringreminder.compose.PlantCreationScreen

fun NavGraphBuilder.plantNavGraph(navController: NavController) {
    navigation(
        startDestination = PlantGraph.PLANT_SCREEN,
        route = PlantGraph.PLANT_ROUTE
    ){
        composable(PlantGraph.PLANT_SCREEN){
            PlantCreationScreen()
        }
    }
}

object PlantGraph {
    const val PLANT_ROUTE = "plantCreator"
    const val PLANT_SCREEN = "plantCreatorScreen"
}