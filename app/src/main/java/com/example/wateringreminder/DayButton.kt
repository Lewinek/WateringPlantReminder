package com.example.wateringreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.ui.theme.BackgroundGrey
import com.example.wateringreminder.ui.theme.LightText

@Composable
fun DayButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = BackgroundGrey)
    ) {
        Text(
            text = number,
            modifier = Modifier
                .background(color = BackgroundGrey)
                .padding(vertical = 8.dp),
            color = LightText,
            fontSize = 18.sp
        )
    }
}
