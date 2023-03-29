package com.example.wateringreminder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Plants : BottomBarScreen(
        route = "plants",
        title = "Plants",
        icon = Icons.Default.List
    )

    object Articles : BottomBarScreen(
        route = "Articles",
        title = "Articles",
        icon = Icons.Default.List
    )
}