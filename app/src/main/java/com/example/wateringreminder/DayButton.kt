package com.example.wateringreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
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
        Modifier.clip(RoundedCornerShape(4.dp)),
        colors = ButtonDefaults.buttonColors(
            contentColor = color,
            containerColor = color
        )
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

@Preview
@Composable
fun DayButtonPreview() {
    DayButton(dayNumber = 1, _selectedIndex = 1, onClick = { })
}
