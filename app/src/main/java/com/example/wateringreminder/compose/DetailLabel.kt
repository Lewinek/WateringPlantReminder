package com.example.wateringreminder.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wateringreminder.R
import com.example.wateringreminder.ui.theme.DarkText
import com.example.wateringreminder.ui.theme.Grey
import com.example.wateringreminder.ui.theme.LightText

@Composable
fun DetailLabel(label: String, value: String, icon: Int) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier
            .background(color = LightText)
            .padding(8.dp)) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Grey,
            )
            Column() {
                Text(text = label, color = Grey, maxLines = 1, fontSize = 12.sp)
                Text(text = value, color = DarkText, fontSize = 14.sp)
            }
        }
    }
}

@Preview
@Composable
fun DetailLabelPreview() {
    DetailLabel(label = "WATER", value = "1/week", R.drawable.water_drop)
}