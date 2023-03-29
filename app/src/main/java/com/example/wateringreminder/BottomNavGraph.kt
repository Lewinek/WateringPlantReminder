package com.example.wateringreminder

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wateringreminder.compose.articlelist.ArticlesScreen
import com.example.wateringreminder.compose.plantlist.PlantsScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Plants.route
    ) {
        composable(route = BottomBarScreen.Plants.route) {
            PlantsScreen()
        }
        composable(route = BottomBarScreen.Articles.route) {
            ArticlesScreen()
        }
    }
}