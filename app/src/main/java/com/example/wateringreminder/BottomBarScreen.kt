package com.example.wateringreminder

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: IconResource
) {
    object Plants : BottomBarScreen(
        route = "plants",
        title = "Plants",
        icon = IconResource.fromDrawableResource(R.drawable.shower)
    )

    object Articles : BottomBarScreen(
        route = "articles",
        title = "Articles",
        icon = IconResource.fromDrawableResource(R.drawable.potted_plant_fill)
    )
}