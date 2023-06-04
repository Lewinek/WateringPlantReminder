package com.example.wateringreminder.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.LightText
import com.example.wateringreminder.ui.theme.kanit

@Composable
fun CharacteristicLabel(label: String, value: String, icon: Int) {
    Row() {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = LightText
        )
        Column() {
            Text(
                text = label,
                color = LightText,
                fontFamily = kanit,
                fontSize = 14.sp
            )
            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = kanit,
                fontSize = 18.sp
            )
        }

    }
}

@Preview
@Composable
fun CharacteristicLabelPreview() {
    CharacteristicLabel(
        label = stringResource(id = R.string.label_water),
        value = stringResource(id = R.string.placeholder_watering_day),
        R.drawable.water_drop
    )
}