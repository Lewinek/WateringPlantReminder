package com.example.wateringreminder.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val AppDarkColorScheme = darkColorScheme(
    primary = Purple200,
    secondary = Teal200
)

private val AppLightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WateringReminderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val appColorScheme = if (darkTheme) {
        AppDarkColorScheme
    } else {
        AppLightColorScheme
    }

    MaterialTheme(
        colorScheme = appColorScheme,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)