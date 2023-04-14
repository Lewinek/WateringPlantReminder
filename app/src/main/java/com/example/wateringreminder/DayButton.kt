package com.example.wateringreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.ui.theme.BackgroundGrey
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.LightText

@Composable
fun DayButton(dayNumber: Int, _selectedIndex: Int, onClick: (Int) -> Unit) {

    var selectedIndex = _selectedIndex
    val color = if (selectedIndex == dayNumber) DarkText else BackgroundGrey

    Button(
        onClick = {
            selectedIndex = if (selectedIndex == dayNumber) -1 else dayNumber
            onClick(selectedIndex)
        },
        Modifier.clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(
            text = dayNumber.toString(),
            modifier = Modifier
                .background(color = color)
                .padding(vertical = 8.dp),
            color = LightText,
            fontSize = 18.sp
        )
    }
}
