package com.example.wateringreminder.compose.navigation

import com.example.wateringreminder.compose.IconResource
import com.example.wateringreminder.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: IconResource
) {
    object Watering : BottomBarScreen(
        route = "watering",
        title = "Watering",
        icon = IconResource.fromDrawableResource(R.drawable.shower)
    )

    object MyPlants : BottomBarScreen(
        route = "myPlants",
        title = "MyPlants",
        icon = IconResource.fromDrawableResource(R.drawable.potted_plant_fill)
    )
}